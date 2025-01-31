package com.estorebookshop.controller.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.config.service.EmailService;
import com.estorebookshop.model.Address;
import com.estorebookshop.model.Cart;
import com.estorebookshop.model.CartItem;
import com.estorebookshop.model.User;
import com.estorebookshop.model.Order;
import com.estorebookshop.model.OrderDetail;
import com.estorebookshop.service.AddressService;
import com.estorebookshop.service.CartItemService;
import com.estorebookshop.service.CartService;
import com.estorebookshop.service.OrderDetailService;
import com.estorebookshop.service.OrderService;
import com.estorebookshop.service.UserService;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/user/cart")
public class MyCartController {

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

	@RequestMapping("")
	public String cart(Model model, RedirectAttributes redirectAttributes) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = this.userService.findByUsername(username);

		try {
			Cart cart = this.cartService.findByUserId(user.getId());
			if (cart == null || cart.getId() == null) {
				redirectAttributes.addFlashAttribute("error", "Cart is empty");
				return "redirect:/";
			}

			List<CartItem> cartItems = cartItemService.findAllByCartId(cart.getId());

			List<Address> addresses = this.addressService.findByUser(user);

			model.addAttribute("cartItems", cartItems);
			model.addAttribute("addresses", addresses);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error fetching cart: " + e.getMessage());
			return "redirect:/user/cart";
		}

		return "user/cart";
	}

	@PostMapping("/update")
	public String update(@RequestParam Map<String, String> quantities, RedirectAttributes redirectAttributes) {
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
			for (CartItem cartItem : cartItems) {
				String quantityStr = quantities.get("quantities[" + cartItem.getId() + "]");
				if (quantityStr != null) {
					Long quantity = Long.parseLong(quantityStr);
					cartItem.setQuantity(quantity);
				}
			}

			this.cartItemService.saveCartItems(cartItems);

			redirectAttributes.addFlashAttribute("message", "Cart updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating cart: " + e.getMessage());
		}

		return "redirect:/user/cart";
	}

	@PostMapping("/pay")
	public String pay(@RequestParam Map<String, String> quantities, @RequestParam("addressId") Long addressId,
			@RequestParam("note") String note, RedirectAttributes redirectAttributes) {

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
			for (CartItem cartItem : cartItems) {
				String quantityStr = quantities.get("quantities[" + cartItem.getId() + "]");
				if (quantityStr != null) {
					long quantity = Long.parseLong(quantityStr);
					if (quantity <= 0) {
						throw new IllegalArgumentException("Quantity must be greater than 0.");
					}
					cartItem.setQuantity(quantity);
				}
			}

			this.cartItemService.saveCartItems(cartItems);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating cart: " + e.getMessage());
			return "redirect:/user/cart";
		}

		List<CartItem> updatedCartItems = cartItemService.findAllByCartId(cart.getId());
		Order order = new Order();
		order.setAddress(this.addressService.findById(addressId));
		order.setCreatedAt(LocalDateTime.now());
		order.setStatus("Pending");
		order.setUser(user);
		order.setNote(note);
		order.setPaymentMethod("COD");

		try {
			BigDecimal totalPrice = BigDecimal.ZERO;
			List<OrderDetail> orderDetails = new ArrayList<>();

			for (CartItem cartItem : updatedCartItems) {
				BigDecimal bookPrice = cartItem.getBook().getPrice();
				BigDecimal discount = cartItem.getBook().getDiscount(); // Giả sử bạn có trường discount trong Book

				// Kiểm tra giá sách và discount hợp lệ
				if (bookPrice == null || bookPrice.compareTo(BigDecimal.ZERO) <= 0) {
					throw new IllegalArgumentException("Invalid price for book: " + cartItem.getBook().getTitle());
				}

				// Tính toán giá sau khi giảm giá (nếu có)
				BigDecimal itemTotal = bookPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
				if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
					itemTotal = itemTotal.subtract(
							itemTotal.multiply(discount).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
				}

				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setBook(cartItem.getBook());
				orderDetail.setQuantity(cartItem.getQuantity());
				orderDetail.setPrice(itemTotal);

				totalPrice = totalPrice.add(itemTotal);
				orderDetails.add(orderDetail);
			}

			order.setTotalPrice(totalPrice);

			this.orderService.save(order);

			// Save order details
			for (OrderDetail orderDetail : orderDetails) {
				orderDetail.setOrder(order);
				this.orderDetailService.save(orderDetail);
			}

			// Clear the cart
			this.cartItemService.deleteByCartId(cart.getId());

			sendOrderDetailsEmail(user, order, orderDetails, redirectAttributes);

			redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error placing order: " + e.getMessage());
			return "redirect:/user/cart";
		}

		return "redirect:/user/order";
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
