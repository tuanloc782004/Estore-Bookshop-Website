package com.myscp.controller.admin;

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

import com.myscp.model.User;
import com.myscp.service.StorageService;
import com.myscp.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@RequestMapping("")
	public String user(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageno", defaultValue = "1") Integer pageno) {

		Page<User> list = this.userService.getAll(pageno);

		if (keyword != null) {
			list = this.userService.searchUser(keyword, pageno);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageno);

		model.addAttribute("users", list);
		return "admin/user/user";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		User user = this.userService.getUserById(id);
		model.addAttribute("user", user);
		return "admin/user/user-form";
	}
	
	@PostMapping("/edit")
	public String update(@ModelAttribute("user") User user, @RequestParam("file") MultipartFile file) {
	    if (!file.isEmpty()) {
	        // Tạo tên file avatarUrl
	        String avatarUrl = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_" + file.getOriginalFilename();
	        // Lưu file với tên avatarUrl
	        this.storageService.store(file, avatarUrl); // Gọi phương thức lưu với tên file mới
	        user.setAvatarUrl(avatarUrl); // Cập nhật avatarUrl trong user
	    }

	    this.userService.updateUser(user);
	    return "redirect:/admin/user";
	}


	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.userService.deleteUser(id);
		return "redirect:/admin/user";
	}

}
