package com.estorebookshop.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.model.Role;
import com.estorebookshop.model.User;
import com.estorebookshop.service.RoleService;
import com.estorebookshop.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@GetMapping("")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "auth/register";
	}

	@PostMapping("")
	public String register(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("rePassword") String rePassword, @RequestParam("email") String email,
			@RequestParam("phoneNumber") String phoneNumber, RedirectAttributes redirectAttributes) {
		try {
			if (!password.equals(rePassword)) {
				redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match.");
				return "redirect:/register";
			}

			if (userService.findByUsername(username) != null) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Username already exists. Please choose a different username.");
				return "redirect:/register";
			}

			if (userService.findByEmail(email) != null) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Email already exists. Please use a different email.");
				return "redirect:/register";
			}

			if (userService.findByPhoneNumber(phoneNumber) != null) {
				redirectAttributes.addFlashAttribute("errorMessage",
						"Phone number already exists. Please use a different phone number.");
				return "redirect:/register";
			}

			Role role = this.roleService.findById(Long.valueOf(2));

			User user = new User();
			user.setAvatarUrl("/upload-dir/default-image/user-avatar.jfif");
			user.setUsername(username);
			user.setPassword(new BCryptPasswordEncoder().encode(password));
			user.setEmail(email);
			user.setPhoneNumber(phoneNumber);
			user.setEnabled(true);
			user.setCreatedAt(LocalDateTime.now());
			user.setRole(role);
			userService.save(user);

			redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now log in.");
			return "redirect:/register";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during registration.");
			return "redirect:/register";
		}
	}

}