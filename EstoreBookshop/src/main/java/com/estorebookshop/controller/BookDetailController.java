package com.estorebookshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.Cart;
import com.estorebookshop.model.CartItem;
import com.estorebookshop.model.Review;
import com.estorebookshop.model.User;
import com.estorebookshop.service.BookService;
import com.estorebookshop.service.CartItemService;
import com.estorebookshop.service.CartService;
import com.estorebookshop.service.ReviewService;
import com.estorebookshop.service.UserService;

@Controller
@RequestMapping("/book-detail")
public class BookDetailController {

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartItemService cartItemService;

	@GetMapping("/{id}")
	public String book(Model model, @PathVariable("id") Long id) {

		Book book = this.bookService.findById(id);
		model.addAttribute("book", book);

		List<Review> reviews = this.reviewService.findByBookIdSortByCreatedAt(id);
		model.addAttribute("reviews", reviews);

		int count = reviews.size();
		model.addAttribute("count", count);

		return "book-detail";
	}

	@PostMapping("/add-to-cart")
	public String addToCart(Model model, @RequestParam Long bookId, @RequestParam Long quantity,
			RedirectAttributes redirectAttributes) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/login";
		}

		String username = authentication.getName();

		User user = this.userService.findByUsername(username);
		Long userId = user.getId();

		if (this.cartService.findByUserId(userId) == null) {
			Cart temp = new Cart();
			temp.setUser(user);
			cartService.save(temp);
		}

		Cart cart = this.cartService.findByUserId(userId);
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		if (quantity != 1) {
			cartItem.setQuantity(quantity - 1);
		}
		if (cartItem.getQuantity() == null) {
		    cartItem.setQuantity(Long.valueOf(1)); 
		}

		cartItem.setBook(this.bookService.findById(bookId));
		this.cartItemService.save(cartItem);

		redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");

		return "redirect:/book-detail/" + bookId;

	}
}
