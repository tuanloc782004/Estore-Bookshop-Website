package com.estorebookshop.controller.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

		// Sửa kiểu trả về để phù hợp với `int`
		int totalQuantity = cartItems.stream().mapToInt(cartItem -> cartItem.getQuantity().intValue()).sum();

		// Sử dụng BigDecimal để tính tổng giá với chính xác 2 chữ số thập phân
		BigDecimal totalPrice = cartItems.stream().map(cartItem -> {
			// Tính giá trị từng mặt hàng
			BigDecimal price = cartItem.getBook().getPrice();
			BigDecimal discount = cartItem.getBook().getDiscount();
			BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
			BigDecimal priceAfterDiscount = price
					.multiply(BigDecimal.valueOf(1).subtract(discount.divide(BigDecimal.valueOf(100))));
			return priceAfterDiscount.multiply(quantity);
		}).reduce(BigDecimal.ZERO, BigDecimal::add);

		// Làm tròn tổng giá về 2 chữ số thập phân
		totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
		
		model.addAttribute("username", this.cartService.findById(cartId).getUser().getUsername());
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalQuantity", totalQuantity);
		model.addAttribute("totalPrice", totalPrice);

		return "admin/cart-item/cart-item";
	}

}
