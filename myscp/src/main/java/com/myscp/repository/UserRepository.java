package com.myscp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myscp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
}