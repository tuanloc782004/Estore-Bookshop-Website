package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Book;
import com.estorebookshop.model.Cart;
import com.estorebookshop.model.CartItem;
import com.estorebookshop.repository.CartItemRepository;
import com.estorebookshop.service.CartItemService;

import jakarta.transaction.Transactional;

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
	@Transactional
	public void deleteByCartId(Long id) {
		// TODO Auto-generated method stub
		this.cartItemRepository.deleteByCartId(id);
	}

	@Override
	public CartItem save(CartItem cartItem) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.save(cartItem);
	}

	@Override
	public void saveCartItems(List<CartItem> cartItems) {
		// TODO Auto-generated method stub
		for (CartItem cartItem : cartItems) {
			CartItem item = cartItemRepository.findById(cartItem.getId()).orElse(null);
			if (item != null) {
				if (cartItem.getQuantity() == 0) {
					this.cartItemRepository.delete(item);
				} else {
					item.setQuantity(cartItem.getQuantity());
	                cartItemRepository.save(item);
				}
            }
		}
	}

	@Override
	public CartItem findByCartAndBook(Cart cart, Book book) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findByCartAndBook(cart, book);
	}

	@Override
	public CartItem findById(Long id) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findById(id).orElse(null);
	}

}
