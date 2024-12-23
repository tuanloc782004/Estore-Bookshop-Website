package com.myscp.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.myscp.model.User;

public interface UserService {

	User findByUsername(String username);

	public User getUserById(Long id);

	public User updateUser(User user);

	public void deleteUser(Long id);

	public List<User> searchUser(String keyword);

	public Page<User> getAll(Integer pageno);

	public Page<User> searchUser(String keyword, Integer pageNo);

}
