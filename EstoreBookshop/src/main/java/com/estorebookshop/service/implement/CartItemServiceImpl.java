package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.CartItem;
import com.estorebookshop.repository.CartItemRepository;
import com.estorebookshop.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public List<CartItem> findAllByCartId(Long cartId) {
		// TODO Auto-generated method stub
		return cartItemRepository.findAll().stream().filter(cartItem -> cartItem.getCart().getId().equals(cartId))
				.toList();
	}

	@Override
	public void deleteByCartId(Long id) {
		// TODO Auto-generated method stub
		this.cartItemRepository.deleteByCartId(id);
	}

}
