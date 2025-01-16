package com.estorebookshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.Category;
import com.estorebookshop.model.Language;
import com.estorebookshop.service.BookService;
import com.estorebookshop.service.CategoryService;
import com.estorebookshop.service.LanguageService;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	private BookService bookService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("")
	public String home(@RequestParam(value = "language", required = false) Long languageId,
			@RequestParam(value = "category", required = false) Long categoryId,
			@RequestParam(value = "sort", defaultValue = "popular") String sort, Model model,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		List<Language> languages = languageService.findAll();
		List<Category> categories = categoryService.findAll();

		Page<Book> list = this.bookService.findAllForHome(pageNo);

		if (languageId != null || categoryId != null || sort != null) {
			list = this.bookService.findForHome(languageId, categoryId, sort, pageNo);
		}

		StringBuilder url = new StringBuilder();

		if (languageId != null) {
			url.append("?language=").append(languageId);
		}
		if (categoryId != null) {
			if (url.length() > 0)
				url.append("&");
			url.append("category=").append(categoryId);
		}
		if (sort != null && !sort.isEmpty()) {
			if (url.length() > 0)
				url.append("&");
			url.append("sort=").append(sort);
		}

		// Add pageNo
		if (url.length() > 0)
			url.append("&");
		url.append("pageNo=").append(pageNo);

		model.addAttribute("paginationUrl", url.toString());

		model.addAttribute("languages", languages);
		model.addAttribute("categories", categories);
		model.addAttribute("selectedLanguageId", languageId);
		model.addAttribute("selectedCategoryId", categoryId);
		model.addAttribute("selectedSort", sort);

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("books", list);
		return "home";
	}

}
