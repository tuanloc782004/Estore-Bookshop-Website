package com.estorebookshop.controller.admin;

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

import com.estorebookshop.model.Publisher;
import com.estorebookshop.service.PublisherService;

@Controller
@RequestMapping("/admin/publisher")
public class PublisherController {
	
	@Autowired
	private PublisherService publisherService;

	@RequestMapping("")
	public String publisher(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Publisher> list = this.publisherService.findAll(pageNo);

		if (keyword != null) {
			list = this.publisherService.findByKeyword(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("publishers", list);
		return "admin/publisher/publisher";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Publisher publisher = new Publisher();
		model.addAttribute("publisher", publisher);
		return "admin/publisher/publisher-form";
	}

	@PostMapping("/add")
	public String save(@ModelAttribute("publisher") Publisher publisher) {
		this.publisherService.save(publisher);
		return "redirect:/admin/publisher";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Publisher publisher = this.publisherService.findById(id);
		model.addAttribute("publisher", publisher);
		return "admin/publisher/publisher-form";
	}

	@PostMapping("/edit")
	public String update(@ModelAttribute("publisher") Publisher publisher) {
		this.publisherService.save(publisher);
		return "redirect:/admin/publisher";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.publisherService.deleteById(id);
		return "redirect:/admin/publisher";
	}
}
