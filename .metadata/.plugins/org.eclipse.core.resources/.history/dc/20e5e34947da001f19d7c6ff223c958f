package com.estorebookshop.controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.service.EmailService;
import com.estorebookshop.config.service.StorageService;
import com.estorebookshop.model.User;
import com.estorebookshop.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private EmailService emailService;

	@RequestMapping("")
	public String user(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<User> list = this.userService.findAll(pageNo);

		if (keyword != null) {
			list = this.userService.findByKeyword(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("users", list);
		return "admin/user/user";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = this.userService.findById(id);
		model.addAttribute("user", user);
		return "admin/user/user-form";
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			if (!file.isEmpty()) {
				String avatarUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_"
						+ file.getOriginalFilename();
				this.storageService.store(file, avatarUrl);
				user.setAvatarUrl("/upload-dir/" + avatarUrl);
			} else {
				user.setAvatarUrl(this.userService.findById(user.getId()).getAvatarUrl());
			}

			this.userService.save(user);
			redirectAttributes.addFlashAttribute("message", "User updated successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "An error occurred while updating the user.");
		}

		return "redirect:/admin/user";
	}

	@GetMapping("/set-enabled/{id}")
	public String setEnabled(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		// Tìm User
		User user = this.userService.findById(id);
		if (user == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
			return "redirect:/admin/user";
		}

		// Cập nhật trạng thái
		user.setEnabled(!user.isEnabled());
		this.userService.save(user);

		// Gửi email thông báo
		String email = user.getEmail();
		String username = user.getUsername();

		try {
			sendNotificationEmail(user, email, username);
			String action = user.isEnabled() ? "activated" : "disabled";
			redirectAttributes.addFlashAttribute("message",
					String.format("User account has been successfully %s.", action));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "An error occurred while sending the email.");
		}

		return "redirect:/admin/user";
	}

	private void sendNotificationEmail(User user, String email, String username) throws Exception {
		if (user.isEnabled()) {
			emailService.sendEmail(email, "Account Activated - Estore Bookshop", String.format(
					"Dear %s,<br><br>Your account has been successfully enabled. You can now log in and use our services.<br><br>Best regards,<br>Estore Bookshop",
					username));
		} else {
			emailService.sendEmail(email, "Account Disabled - Estore Bookshop", String.format(
					"Dear %s,<br><br>Your account has been disabled by the administrator. If you believe this is a mistake or need assistance, please contact us at <a href='mailto:jobhere.22t.nhat1@gmail.com'>jobhere.22t.nhat1@gmail.com</a>.<br><br>Best regards,<br>Estore Bookshop",
					username));
		}
	}

}
