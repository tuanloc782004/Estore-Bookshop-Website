package com.estorebookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.Cart;
import com.estorebookshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteByCartId(Long id);
	
	CartItem findByCartAndBook(Cart cart, Book book);
	
}
