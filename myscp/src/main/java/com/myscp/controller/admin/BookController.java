package com.myscp.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myscp.model.Book;
import com.myscp.model.Category;
import com.myscp.model.Language;
import com.myscp.service.BookService;
import com.myscp.service.CategoryService;
import com.myscp.service.LanguageService;

@Controller
@RequestMapping("/admin/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("")
	public String book(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageno", defaultValue = "1") Integer pageno) {
		Page<Book> list = this.bookService.getAll(pageno);

		if (keyword != null) {
			list = this.bookService.searchBook(keyword, pageno);
			model.addAttribute("keyword", keyword);
		}

		List<String> firstImageUrls = new ArrayList<>();
		for (Book book : list) {
			if (book.getImages() != null && !book.getImages().isEmpty()) {
				firstImageUrls.add("/upload-dir/" + new ArrayList<>(book.getImages()).get(0).getImageUrl());
			} else {
				firstImageUrls.add("/upload-dir/default-image.jfif");
			}
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageno);
		model.addAttribute("books", list);
		model.addAttribute("firstImageUrls", firstImageUrls);
		return "admin/book/book";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		Book book = new Book();
		List<Language> languages = this.languageService.getAllLanguages();
        List<Category> categories = this.categoryService.getAllCategories();
		model.addAttribute("book", book);
		model.addAttribute("categories", categories);
		model.addAttribute("languages", languages);
		return "admin/book/book-form";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Book book = this.bookService.getBookById(id);
		List<Language> languages = languageService.getAllLanguages();
        List<Category> categories = categoryService.getAllCategories();
		model.addAttribute("book", book);
		model.addAttribute("categories", categories);
		model.addAttribute("languages", languages);
		return "admin/book/book-form";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.bookService.deleteBook(id);
		return "redirect:/admin/book";
	}

}
