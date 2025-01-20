package com.estorebookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estorebookshop.model.Book;
import com.estorebookshop.service.BookService;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private BookService bookService;

	@RequestMapping("")
	public String home(@Param("keyword") String keyword,
			@RequestParam(value = "sort", defaultValue = "popular") String sort, Model model,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Book> list = this.bookService.findAllForHome(pageNo);

		if (keyword != null || sort != null) {
			list = this.bookService.findForSearch(keyword, sort, pageNo);
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("sort", sort);

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("books", list);

		return "search";
	}

}
