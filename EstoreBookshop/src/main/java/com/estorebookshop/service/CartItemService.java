package com.estorebookshop.service;

import java.util.List;

import com.estorebookshop.model.CartItem;

public interface CartItemService {
 
	public List<CartItem> findAllByCartId(Long cartId);
	
}
