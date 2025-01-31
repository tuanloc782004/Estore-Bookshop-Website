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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public String category(Model model, @Param("keyword") String keyword,
                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<Category> list = null;
        try {
            list = this.categoryService.findAll(pageNo);

            if (keyword != null) {
                list = this.categoryService.findByKeyword(keyword, pageNo);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("totalPage", list.getTotalPages());
            model.addAttribute("currentPage", pageNo);
            model.addAttribute("categories", list);

        } catch (Exception e) {
            model.addAttribute("message", "Error occurred while fetching categories: " + e.getMessage());
        }
        
        model.addAttribute("current", "category");

        return "admin/category/category";
    }

    @GetMapping("/add")
    public String add(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        
        model.addAttribute("current", "category");
        
        return "admin/category/category-form";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.save(category);
            redirectAttributes.addFlashAttribute("message", "Category added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while adding category: " + e.getMessage());
            return "redirect:/admin/category/add";
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Category category = null;
        model.addAttribute("current", "category");
        try {
            category = this.categoryService.findById(id);
            model.addAttribute("category", category);
        } catch (Exception e) {
            model.addAttribute("error", "Error occurred while fetching category: " + e.getMessage());
            return "admin/category/category";
        }
        return "admin/category/category-form";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.save(category);
            redirectAttributes.addFlashAttribute("message", "Category updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while updating category: " + e.getMessage());
            return "redirect:/admin/category/edit/" + category.getId();
        }
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Category deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred while deleting category: " + e.getMessage());
        }
        return "redirect:/admin/category";
    }
}
