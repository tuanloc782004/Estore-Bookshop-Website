package com.estorebookshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estorebookshop.model.Category;
import com.estorebookshop.service.CategoryService;

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
	public String category(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Category> list = this.categoryService.findAll(pageNo);

		if (keyword != null) {
			list = this.categoryService.findByKeyword(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

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
	public String create(@ModelAttribute("category") Category category) {
		this.categoryService.save(category);
		return "redirect:/admin/category";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Category category = this.categoryService.findById(id);
		model.addAttribute("category", category);
		return "admin/category/category-form";
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("category") Category category) {
		this.categoryService.save(category);
		return "redirect:/admin/category";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.categoryService.deleteById(id);
		return "redirect:/admin/category";
	}

}
