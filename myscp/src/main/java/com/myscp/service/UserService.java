package com.myscp.service;

import com.myscp.model.User;

public interface UserService {

	User findByUsername(String username);
	
}