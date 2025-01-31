package com.estorebookshop.controller.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
import com.estorebookshop.model.Review;
import com.estorebookshop.model.User;
import com.estorebookshop.service.ReviewService;

@Controller
@RequestMapping("/admin/review")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private EmailService emailService;

	@RequestMapping("")
	public String review(@RequestParam(value = "checked", defaultValue = "all") String checked,
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

		Page<Review> list = this.reviewService.findAll(pageNo);

		if (checked != null || startDateTime != null || endDateTime != null) {
			list = this.reviewService.findByKeyword(checked, startDateTime, endDateTime, pageNo);
		}

		model.addAttribute("checked", checked);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("reviews", list);

		model.addAttribute("current", "review");

		return "admin/review/review";
	}

	@GetMapping("/delete-comment")
	public String deleteComment(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		Review review = this.reviewService.findById(id);
		User user = review.getUser();

		String email = review.getUser().getEmail();
		String username = review.getUser().getUsername();
		String title = review.getBook().getTitle();

		try {
			this.reviewService.deleteComment(id);

			sendNotificationEmail(user, email, username, title, id);

			redirectAttributes.addFlashAttribute("message", "Comment has been successfully deleted.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to delete the comment.");
		}

		return "redirect:/admin/review";
	}

	@GetMapping("/set-status")
	public String setStatus(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		try {
			this.reviewService.setStatus(id);

			redirectAttributes.addFlashAttribute("message", "Accepted successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failure accepted.");
		}

		return "redirect:/admin/review";
	}

	private void sendNotificationEmail(User user, String email, String username, String title, Long orderId)
			throws Exception {
		String subject = "Comment Removal Notification - Estore Bookshop";
		String message = String.format(
				"Dear %s,<br><br>Your comment on the book <b>%s</b> has been removed due to a violation of our community standards.<br>"
						+ "Comment ID: <b>%d</b><br><br>"
						+ "We encourage you to review our community guidelines to ensure that your future comments comply with our standards.<br><br>"
						+ "Best regards,<br>Estore Bookshop",
				username, title, orderId);

		emailService.sendEmail(email, subject, message);
	}

}
