package com.estorebookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estorebookshop.model.Address;
import com.estorebookshop.model.User;

public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("SELECT a FROM Address a JOIN a.user u " + "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(a.address) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(a.city) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(a.zipCode) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(a.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Address> findByKeyword(String keyword);
	
	List<Address> findByUser(User user);
}
