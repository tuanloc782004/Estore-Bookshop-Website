package com.estorebookshop.controller.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.model.Book;
import com.estorebookshop.model.Order;
import com.estorebookshop.model.OrderDetail;
import com.estorebookshop.model.Review;
import com.estorebookshop.model.User;
import com.estorebookshop.service.BookService;
import com.estorebookshop.service.OrderDetailService;
import com.estorebookshop.service.OrderService;
import com.estorebookshop.service.ReviewService;
import com.estorebookshop.service.UserService;

@Controller
@RequestMapping("/user/order")
public class MyOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;

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

		List<Order> orders = this.orderService.findByUser(user);
		orders.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
		model.addAttribute("orders", orders);

		return "user/order";

	}

	@GetMapping("/order-detail/{id}")
	public String orderDetail(@PathVariable("id") Long orderId, Model model) {

		List<OrderDetail> orderDetails = orderDetailService.findAllByOrderId(orderId);

		// Gửi dữ liệu vào view
		model.addAttribute("order", this.orderService.findById(orderId));
		model.addAttribute("orderDetails", orderDetails);

		return "user/order-detail";
	}

	@GetMapping("/cancel/{id}")
	public String cancel(@PathVariable("id") Long orderId, Model model) {

		Order order = this.orderService.findById(orderId);
		order.setStatus("Cancelled");
		this.orderService.save(order);

		return "redirect:/user/order/order-detail/" + orderId;
	}

	@GetMapping("/review/{id}")
	public String book(Model model, @PathVariable("id") Long id) {

		Book book = this.bookService.findById(id);
		model.addAttribute("book", book);

		List<Review> reviews = this.reviewService.findByBookIdSortByCreatedAt(id);
		model.addAttribute("reviews", reviews);

		int count = reviews.size();
		model.addAttribute("count", count);

		return "user/review";
	}

	@PostMapping("/review")
	public String postReview(@RequestParam("bookId") Long bookId, @RequestParam("rating") int rating,
			@RequestParam("comment") String comment, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = this.userService.findByUsername(username);

		// Tìm sách theo ID
		Book book = bookService.findById(bookId);
		if (book != null) {
			// Tạo và lưu review
			Review review = new Review();
			review.setBook(book);
			review.setRating(rating);
			review.setComment(comment);
			review.setChecked("Unchecked");
			review.setCreatedAt(LocalDateTime.now());
			review.setUser(user);
			reviewService.save(review);

			// Thêm thông báo thành công
			model.addAttribute("message", "Your review has been posted successfully!");

			// Hiển thị lại trang chi tiết sách
			return "redirect:/book-detail/" + bookId; // Chuyển hướng đến trang chi tiết sách
		} else {
			// Thêm thông báo lỗi nếu không tìm thấy sách
			model.addAttribute("message", "Book not found!");
			return "redirect:/";
		}
	}

}
