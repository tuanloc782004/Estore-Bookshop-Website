package com.estorebookshop.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Cart;
import com.estorebookshop.repository.CartRepository;
import com.estorebookshop.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		this.cartRepository.deleteById(id);
	}

	@Override
	public List<Cart> findByKeyword(String keyword, String status, LocalDateTime varDate) {
		// TODO Auto-generated method stub
		return this.cartRepository.findByKeyword(keyword, status, varDate);
	}

	@Override
	public Page<Cart> findAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		return this.cartRepository.findAll(pageable);
	}

	@Override
	public Page<Cart> findByKeyword(String keyword, String status, LocalDateTime varDate, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Cart> list = this.cartRepository.findByKeyword(keyword, status, varDate);
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		
		return new PageImpl<Cart>(list, pageable, this.cartRepository.findByKeyword(keyword, status, varDate).size());
	}

	@Override
	public Cart findById(Long id) {
		// TODO Auto-generated method stub
		return this.cartRepository.findById(id).orElse(null);
	}

	@Override
	public Cart findByUserId(Long id) {
		// TODO Auto-generated method stub
		return this.cartRepository.findByUserId(id);
	}

	@Override
	public Cart save(Cart cart) {
		// TODO Auto-generated method stub
		return this.cartRepository.save(cart);
	}

}
