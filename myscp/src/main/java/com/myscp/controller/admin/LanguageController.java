package com.myscp.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myscp.model.Language;
import com.myscp.service.LanguageService;

@Controller
@RequestMapping("/admin/language")
public class LanguageController {

	@Autowired
	private LanguageService languageService;

	@RequestMapping("")
	public String language(Model model) {
		List<Language> languages = this.languageService.getAllLanguages();
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
	public String save(@ModelAttribute("language") Language language) {
		this.languageService.createLanguage(language);
		return "redirect:/admin/language";
	}

	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Language language = this.languageService.getLanguageById(id);
		model.addAttribute("language", language);
		return "admin/language/language-form";
	}
	
	@PostMapping("/edit")
	public String update(@ModelAttribute("language") Language language) {
		this.languageService.updateLanguage(language);
		return "redirect:/admin/language";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.languageService.deleteLanguage(id);
		return "redirect:/admin/language";
	}

}
