package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.User;
import com.estorebookshop.repository.UserRepository;
import com.estorebookshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return this.userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return this.userRepository.findById(id).orElse(null);
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return this.userRepository.save(user);
	}

	@Override
	public List<User> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.userRepository.findByKeyword(keyword);
	}

	@Override
	public Page<User> findAll(Integer pageno) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageno - 1, 5);
		return this.userRepository.findAll(pageable);
	}

	@Override
	public Page<User> findByKeyword(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		List<User> list = this.userRepository.findByKeyword(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, 5);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<User>(list, pageable, this.findByKeyword(keyword).size());
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return this.userRepository.findByEmail(email);
	}

	@Override
	public User findByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return this.userRepository.findByPhoneNumber(phoneNumber);
	}

	@Override
	public long countUsers() {
		// TODO Auto-generated method stub
		return this.userRepository.count();
	}
	
}
