package com.estorebookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estorebookshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username LIKE %?1% OR u.email LIKE %?1% OR u.phoneNumber LIKE %?1%")
    List<User> findByKeyword(String keyword);
	
	User findByEmail(String email);
	
	User findByPhoneNumber(String phoneNumber);
	
	long count();
	
}
