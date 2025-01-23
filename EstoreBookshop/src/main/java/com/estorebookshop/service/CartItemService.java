package com.estorebookshop.service;

import java.util.List;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.Cart;
import com.estorebookshop.model.CartItem;

public interface CartItemService {
 
	public List<CartItem> findAllByCartId(Long cartId);
	
	public void deleteByCartId(Long id);
	
	public CartItem save(CartItem cartItem);
	
	public void saveCartItems(List<CartItem> cartItems);
	
	public CartItem findByCartAndBook(Cart cart, Book book);
	
	public CartItem findById(Long id);
	
}
