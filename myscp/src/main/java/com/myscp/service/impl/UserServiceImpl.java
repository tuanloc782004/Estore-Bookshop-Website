package com.myscp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myscp.model.User;
import com.myscp.repository.UserRepository;
import com.myscp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}
	
	@Override
	public List<User> searchUser(String keyword) {
		return this.userRepository.searchUser(keyword);
	}

	@Override
	public Page<User> getAll(Integer pageno) {
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		return this.userRepository.findAll(pageable);
	}

	@Override
	public Page<User> searchUser(String keyword, Integer pageno) {
		List<User> list = this.searchUser(keyword);
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<User>(list, pageable, this.searchUser(keyword).size());
	}
	
}
