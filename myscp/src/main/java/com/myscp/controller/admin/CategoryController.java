package com.myscp.controller.admin;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myscp.model.Category;
import com.myscp.service.CategoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("")
	public String language(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageno", defaultValue = "1") Integer pageno) {

		Page<Category> list = this.categoryService.getAll(pageno);

		if (keyword != null) {
			list = this.categoryService.searchCategory(keyword, pageno);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageno);

		model.addAttribute("categories", list);
		return "admin/category/category";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "admin/category/category-form";
	}

	@PostMapping("/add")
	public String save(@ModelAttribute("category") Category category) {
		LocalDateTime localDateTime = LocalDateTime.now();
		category.setCreatedAt(localDateTime);

		this.categoryService.createCategory(category);
		return "redirect:/admin/category";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Category category = this.categoryService.getCategoryById(id);
		model.addAttribute("category", category);
		return "admin/category/category-form";
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("category") Category category) {
		this.categoryService.updateCategory(category);
		return "redirect:/admin/category";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.categoryService.deleteCategory(id);
		return "redirect:/admin/category";
	}

}
