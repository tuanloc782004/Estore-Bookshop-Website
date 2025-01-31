package com.estorebookshop.controller.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.config.service.EmailService;
import com.estorebookshop.model.Cart;
import com.estorebookshop.model.CartItem;
import com.estorebookshop.model.Order;
import com.estorebookshop.model.OrderDetail;
import com.estorebookshop.model.User;
import com.estorebookshop.service.AddressService;
import com.estorebookshop.service.CartItemService;
import com.estorebookshop.service.CartService;
import com.estorebookshop.service.OrderDetailService;
import com.estorebookshop.service.OrderService;
import com.estorebookshop.service.UserService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/paypal")
public class PayPalController {

	@Autowired
	private APIContext apiContext;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private EmailService emailService;

	@PostMapping("/pay")
	public RedirectView pay(@RequestParam Map<String, String> quantities, @RequestParam("addressId") Long addressId,
			@RequestParam("note") String note, RedirectAttributes redirectAttributes, HttpSession session) {
		session.setAttribute("addressId", addressId);
		session.setAttribute("note", note);
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = this.userService.findByUsername(username);

		Cart cart = this.cartService.findByUserId(user.getId());

		List<CartItem> cartItems = cartItemService.findAllByCartId(cart.getId());

		try {
			// Cập nhật số lượng giỏ hàng
			for (CartItem cartItem : cartItems) {
				String quantityStr = quantities.get("quantities[" + cartItem.getId() + "]");
				if (quantityStr != null) {
					Long quantity = Long.parseLong(quantityStr);
					cartItem.setQuantity(quantity);
				}
			}
			this.cartItemService.saveCartItems(cartItems);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating cart: " + e.getMessage());
			return new RedirectView("/user/cart");
		}

		try {
			// Tính tổng tiền
			BigDecimal totalPrice = BigDecimal.ZERO;
			for (CartItem cartItem : cartItems) {
				BigDecimal bookPrice = cartItem.getBook().getPrice();
	            BigDecimal discountRate = BigDecimal.valueOf(100).subtract(cartItem.getBook().getDiscount());
	            BigDecimal discountedPrice = bookPrice.multiply(discountRate).divide(BigDecimal.valueOf(100));
	            BigDecimal itemTotal = discountedPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
	            totalPrice = totalPrice.add(itemTotal);
			}

			// Tạo đối tượng thanh toán PayPal
			Amount amount = new Amount();
			amount.setCurrency("USD");
			amount.setTotal(String.format("%.2f", totalPrice));

			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setDescription("Book purchase");

			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(List.of(transaction));

			RedirectUrls redirectUrls = new RedirectUrls();
			redirectUrls.setCancelUrl("http://localhost:8080/user/paypal/cancel");
			redirectUrls.setReturnUrl("http://localhost:8080/user/paypal/success");
			payment.setRedirectUrls(redirectUrls);

			Payment createdPayment = payment.create(apiContext);

			// Lấy link thanh toán
			for (Links link : createdPayment.getLinks()) {
				if ("approval_url".equals(link.getRel())) {
					return new RedirectView(link.getHref());
				}
			}

			throw new Exception("Approval URL not found.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error processing PayPal payment: " + e.getMessage());
			return new RedirectView("/user/cart");
		}
	}

	@GetMapping("/success")
	public RedirectView success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
	                             RedirectAttributes redirectAttributes, HttpSession session) {
	    Long addressId = (Long) session.getAttribute("addressId");
	    String note = (String) session.getAttribute("note");

	    try {
	        Payment payment = Payment.get(apiContext, paymentId);
	        PaymentExecution paymentExecution = new PaymentExecution();
	        paymentExecution.setPayerId(payerId);
	        Payment executedPayment = payment.execute(apiContext, paymentExecution);

	        // Tạo Order mới
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String username = (principal instanceof CustomUserDetails) ? ((CustomUserDetails) principal).getUsername()
	                : principal.toString();

	        User user = this.userService.findByUsername(username);
	        Cart cart = this.cartService.findByUserId(user.getId());
	        List<CartItem> cartItems = this.cartItemService.findAllByCartId(cart.getId());

	        Order order = new Order();
	        order.setAddress(this.addressService.findById(addressId));
	        order.setCreatedAt(LocalDateTime.now());
	        order.setStatus("Pending");
	        order.setUser(user);
	        order.setNote(note);
	        order.setPaymentMethod("Paypal");
	        order.setTotalPrice(new BigDecimal(executedPayment.getTransactions().get(0).getAmount().getTotal()));

	        this.orderService.save(order);
	        
	        List<OrderDetail> orderDetails = new ArrayList<>();

	        // Lưu chi tiết đơn hàng
	        for (CartItem cartItem : cartItems) {
	            OrderDetail orderDetail = new OrderDetail();
	            orderDetail.setOrder(order);
	            orderDetail.setBook(cartItem.getBook());
	            orderDetail.setQuantity(cartItem.getQuantity());
	            
	            BigDecimal bookPrice = cartItem.getBook().getPrice();
	            BigDecimal discountRate = BigDecimal.valueOf(100).subtract(cartItem.getBook().getDiscount());
	            BigDecimal discountedPrice = bookPrice.multiply(discountRate).divide(BigDecimal.valueOf(100));
	            orderDetail.setPrice(discountedPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));

	            this.orderDetailService.save(orderDetail);
	            orderDetails.add(orderDetail);
	        }

	        // Xóa giỏ hàng
	        this.cartItemService.deleteByCartId(cart.getId());

	        session.removeAttribute("addressId");
	        session.removeAttribute("note");
	        
	        sendOrderDetailsEmail(user, order, orderDetails, redirectAttributes);

	        redirectAttributes.addFlashAttribute("success", "Order placed successfully!");

	        // Chuyển hướng đến trang /user/order
	        return new RedirectView("/user/order");
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("error", "Payment verification failed: " + e.getMessage());
	        return new RedirectView("/user/cart");
	    }
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "Payment canceled.";
	}
	
	private void sendOrderDetailsEmail(User user, Order order, List<OrderDetail> orderDetails,
			RedirectAttributes redirectAttributes) {
		String email = user.getEmail();
		String username = user.getUsername();

		String subject = "Your Order Details - Estore Bookshop";
		StringBuilder message = new StringBuilder();

		message.append(String.format("Dear %s,<br><br>", username));
		message.append("Thank you for your purchase! Here are the details of your order:<br><br>");
		message.append(String.format("Order ID: <b>%d</b><br>", order.getId()));
		message.append(String.format("Order Date: <b>%s</b><br>",
				order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
		message.append("Order Details:<br><table border='1' cellpadding='5' cellspacing='0'>");
		message.append("<tr><th>Book</th><th>Quantity</th><th>Price</th></tr>");

		for (OrderDetail orderDetail : orderDetails) {
			message.append(String.format("<tr><td>%s</td><td>%d</td><td>$%.2f</td></tr>",
					orderDetail.getBook().getTitle(), orderDetail.getQuantity(), orderDetail.getPrice()));
		}

		message.append("</table>");
		message.append(String.format("<br><b>Total Price: $%.2f</b><br><br>", order.getTotalPrice()));
		message.append("We hope you enjoy your books!<br><br>Best regards,<br>Estore Bookshop");

		try {
			this.emailService.sendEmail(email, subject, message.toString());
		} catch (MessagingException e) {
			System.err.println("Error sending email: " + e.getMessage());
			redirectAttributes.addFlashAttribute("warning",
					"Order placed successfully, but failed to send confirmation email.");
		}

	}
}
