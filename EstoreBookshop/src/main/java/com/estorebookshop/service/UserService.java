package com.estorebookshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.User;

public interface UserService {

	User findByUsername(String username); 
	
	public User findById(Long id);

	public User save(User user);

	public List<User> findByKeyword(String keyword);

	public Page<User> findAll(Integer pageno);

	public Page<User> findByKeyword(String keyword, Integer pageNo);
	
}
