package com.estorebookshop.controller.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.service.EmailService;
import com.estorebookshop.model.Book;
import com.estorebookshop.model.Order;
import com.estorebookshop.model.OrderDetail;
import com.estorebookshop.model.User;
import com.estorebookshop.service.BookService;
import com.estorebookshop.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private BookService bookService;

	@Autowired
	private EmailService emailService;

	@RequestMapping("")
	public String order(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "status", required = false, defaultValue = "All") String status,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, Model model) {

		if (startDate == null) {
			startDate = LocalDate.of(2016, 1, 1);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}

		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

		Page<Order> list = this.orderService.findAll(pageNo);

		if (username != null || status != null || startDateTime != null || endDateTime != null) {
			list = this.orderService.findByKeyword(username, status, startDateTime, endDateTime, pageNo);
		}

		model.addAttribute("username", username);
		model.addAttribute("status", status);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("orders", list);
		
		model.addAttribute("current", "order");

		return "admin/order/order";
	}

	@GetMapping("/set-status")
	public String setStatus(@RequestParam Long orderId, @RequestParam String status,
			RedirectAttributes redirectAttributes) {
		Order order = this.orderService.findById(orderId);
		User user = order.getUser();

		String email = user.getEmail();
		String username = user.getUsername();
		String oldStatus = order.getStatus();

		try {
			if ("Pending".equals(oldStatus) && "Processing".equals(status)) {
				for (OrderDetail od : order.getOrderDetails()) {
					Book book = od.getBook();
					Long requestedQuantity = od.getQuantity();

					// Kiểm tra nếu không đủ số lượng sách
					if (book.getQuantity() < requestedQuantity) {
						redirectAttributes.addFlashAttribute("error",
								"Not enough stock for the book: " + book.getTitle());
						return "redirect:/admin/order";
					}

					// Trừ số lượng sách
					book.setQuantity(book.getQuantity() - requestedQuantity);
					
					if (book.getQuantity() == 0 && book.isEnabled()) {
						book.setEnabled(false);
					}
					
					book.setSoldQuantity(book.getSoldQuantity() + requestedQuantity);
					this.bookService.save(book);
				}
			} else if (("Processing".equals(oldStatus) || "Shipped".equals(oldStatus)) && "Cancelled".equals(status)) {
				for (OrderDetail od : order.getOrderDetails()) {
					Book book = od.getBook();
					Long returnedQuantity = od.getQuantity();

					// Hoàn lại số lượng sách khi đơn hàng bị hủy
					book.setQuantity(book.getQuantity() + returnedQuantity);
					
					if (book.getQuantity() > 0 && !book.isEnabled()) {
						book.setEnabled(true);
					}
					
					book.setSoldQuantity(book.getSoldQuantity() - returnedQuantity);
					this.bookService.save(book);
				}
			}

			orderService.setStatus(orderId, status);

			sendNotificationEmail(user, email, username, oldStatus, status, orderId);

			redirectAttributes.addFlashAttribute("message", "Order status updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to update order status: " + e.getMessage());
		}

		return "redirect:/admin/order";
	}

	private void sendNotificationEmail(User user, String email, String username, String oldStatus, String newStatus,
			Long orderId) throws Exception {
		LocalDateTime updateTime = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedUpdateTime = updateTime.format(formatter);

		String subject = "Order Status Updated - Estore Bookshop";
		String message = String.format(
				"Dear %s,<br><br>Your order with ID <b>%d</b> has been updated.<br>"
						+ "The status has changed from <b>%s</b> to <b>%s</b> on <b>%s</b>.<br><br>"
						+ "Thank you for shopping with us!<br><br>Best regards,<br>Estore Bookshop",
				username, orderId, oldStatus, newStatus, formattedUpdateTime);

		emailService.sendEmail(email, subject, message);
	}
}
