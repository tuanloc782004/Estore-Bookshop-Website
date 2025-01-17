package com.estorebookshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.Review;
import com.estorebookshop.service.BookService;
import com.estorebookshop.service.ReviewService;

@Controller
@RequestMapping("/book-detail")
public class BookDetailController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		
		Book book = this.bookService.findById(id);
		model.addAttribute("book", book);

		List<Review> reviews = this.reviewService.findByBookIdSortByCreatedAt(id);
		model.addAttribute("reviews", reviews);
		
		int count = reviews.size();
		model.addAttribute("count", count);

		return "user/book-detail";
	}
}
