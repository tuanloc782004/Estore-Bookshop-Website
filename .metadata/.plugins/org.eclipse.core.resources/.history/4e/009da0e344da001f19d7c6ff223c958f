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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.model.Language;
import com.estorebookshop.service.LanguageService;

@Controller
@RequestMapping("/admin/language")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @RequestMapping("")
    public String language(Model model) {
        List<Language> languages = null;
        try {
            languages = this.languageService.findAll();
            model.addAttribute("languages", languages);
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while fetching languages: " + e.getMessage());
        }
        return "admin/language/language";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Language language = new Language();
        model.addAttribute("language", language);
        return "admin/language/language-form";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("language") Language language, RedirectAttributes redirectAttributes) {
        try {
            this.languageService.save(language);
            redirectAttributes.addFlashAttribute("message", "Language added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while adding language: " + e.getMessage());
            return "redirect:/admin/language/add";
        }
        return "redirect:/admin/language";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Language language = null;
        try {
            language = this.languageService.findById(id);
            model.addAttribute("language", language);
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while fetching language: " + e.getMessage());
            return "admin/language/language";
        }
        return "admin/language/language-form";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("language") Language language, RedirectAttributes redirectAttributes) {
        try {
            this.languageService.save(language);
            redirectAttributes.addFlashAttribute("message", "Language updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while updating language: " + e.getMessage());
            return "redirect:/admin/language/edit/" + language.getId();
        }
        return "redirect:/admin/language";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            this.languageService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Language deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while deleting language: " + e.getMessage());
        }
        return "redirect:/admin/language";
    }
}
