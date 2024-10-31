package com.myscp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myscp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.username LIKE %?1%")
	public List<User> searchUser(String keyWord);
}
