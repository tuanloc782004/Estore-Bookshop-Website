package com.estorebookshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Cart;

public interface CartService {

	public void deleteById(Long id);

	public List<Cart> findByKeyword(String keyword, String status, LocalDateTime compareDate);

	public Page<Cart> findAll(Integer pageNo);

	public Page<Cart> findByKeyword(String keyword, String status, LocalDateTime compareDate, Integer pageNo);
	
	public Cart findById(Long id);
	
	public Cart findByUserId(Long id);
	
	public Cart save(Cart cart);
	
}
