package com.estorebookshop.controller.admin;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estorebookshop.model.Cart;
import com.estorebookshop.service.CartService;

@Controller
@RequestMapping("/admin/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping("")
	public String cart(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "status", required = false, defaultValue = "all") String status,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Cart> list = this.cartService.findAll(pageNo);
		
		LocalDateTime compareDate = LocalDateTime.now().minusDays(90);

		if (keyword != null) {
			list = this.cartService.findByKeyword(keyword, status, compareDate, pageNo);
			model.addAttribute("keyword", keyword);
			model.addAttribute("status", status);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("compareDate", compareDate);
		model.addAttribute("carts", list);
		return "admin/cart/cart";
	}

}
