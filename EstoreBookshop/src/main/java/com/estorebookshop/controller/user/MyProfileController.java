package com.estorebookshop.controller.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.config.service.StorageService;
import com.estorebookshop.model.User;
import com.estorebookshop.service.UserService;

@Controller
@RequestMapping("/user/my-profile")
public class MyProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@RequestMapping("")
	public String myProfile(Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = userService.findByUsername(username);
		if (user != null) {
			model.addAttribute("user", user);
		} else {
			model.addAttribute("error", "User not found!");
		}

		return "user/my-profile";
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof CustomUserDetails) {
				username = ((CustomUserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}

			User user1 = userService.findByUsername(username);

			user1.setEmail(user.getEmail());
			user1.setPhoneNumber(user.getPhoneNumber());

			this.userService.save(user1);

			if (user1 != null) {
				model.addAttribute("user1", user1);
			}

			redirectAttributes.addFlashAttribute("message", "User updated successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "An error occurred while updating the user.");
		}

		return "redirect:/user/my-profile";
	}

	@PostMapping("/upload-avatar")
	public String uploadAvatar(Model model, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username;
			if (principal instanceof CustomUserDetails) {
				username = ((CustomUserDetails) principal).getUsername();
			} else {
				username = principal.toString();
			}

			User user = userService.findByUsername(username);

			if (!file.isEmpty()) {
				String avatarUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_"
						+ file.getOriginalFilename();
				this.storageService.store(file, avatarUrl);

				user.setAvatarUrl("/upload-dir/" + avatarUrl);
			} else {
				user.setAvatarUrl(this.userService.findById(user.getId()).getAvatarUrl());
			}

			this.userService.save(user);

			if (user != null) {
				model.addAttribute("user", user);
			}

			redirectAttributes.addFlashAttribute("message", "Uploaded avatar successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "An error occurred while uploading avatar.");
		}

		return "redirect:/user/my-profile";
	}

	@PostMapping("/change-password")
	public String changePassword(Model model, @RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = userService.findByUsername(username);

		// Kiểm tra mật khẩu hiện tại
		if (!new BCryptPasswordEncoder().matches(currentPassword, user.getPassword())) {
			redirectAttributes.addFlashAttribute("message", "Current password is incorrect.");
			return "redirect:/user/my-profile";
		}

		// Cập nhật mật khẩu mới
		user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
		userService.save(user);

		if (user != null) {
			model.addAttribute("user", user);
		}

		redirectAttributes.addFlashAttribute("message", "Password changed successfully!");
		return "redirect:/user/my-profile";
	}

}
