package com.estorebookshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estorebookshop.model.Language;
import com.estorebookshop.service.LanguageService;

@Controller
@RequestMapping("/admin/language")
public class LanguageController {

	@Autowired
	private LanguageService languageService;

	@RequestMapping("")
	public String language(Model model) {
		List<Language> languages = this.languageService.findAll();
		model.addAttribute("languages", languages);
		return "admin/language/language";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Language language = new Language();
		model.addAttribute("language", language);
		return "admin/language/language-form";
	}

	@PostMapping("/add")
	public String create(@ModelAttribute("language") Language language) {
		this.languageService.save(language);
		return "redirect:/admin/language";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Language language = this.languageService.findById(id);
		model.addAttribute("language", language);
		return "admin/language/language-form";
	}
	
	@PostMapping("/edit")
	public String update(@ModelAttribute("language") Language language) {
		this.languageService.save(language);
		return "redirect:/admin/language";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.languageService.deleteById(id);
		return "redirect:/admin/language";
	}
}
