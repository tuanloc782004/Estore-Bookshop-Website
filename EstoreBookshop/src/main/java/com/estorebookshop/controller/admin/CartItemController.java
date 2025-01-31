package com.estorebookshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estorebookshop.model.CartItem;
import com.estorebookshop.service.CartItemService;
import com.estorebookshop.service.CartService;

@Controller
@RequestMapping("/admin/cart-item")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private CartService cartService;

	@RequestMapping("/{id}")
	public String cartItem(@PathVariable("id") Long cartId, Model model) {
		List<CartItem> cartItems = cartItemService.findAllByCartId(cartId);

		// Gửi dữ liệu vào view
		model.addAttribute("username", this.cartService.findById(cartId).getUser().getUsername());
		model.addAttribute("cartItems", cartItems);
		
		model.addAttribute("current", "cart");

		return "admin/cart-item/cart-item";
	}

}
