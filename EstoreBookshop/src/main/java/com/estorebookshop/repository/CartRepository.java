package com.estorebookshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estorebookshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT c FROM Cart c WHERE c.user.username LIKE %:keyword% AND "
			+ "(:status = 'all' OR (:status = 'active' AND c.updatedAt > :compareDate) OR "
			+ "(:status = 'abandoned' AND c.updatedAt <= :compareDate))")
	List<Cart> findByKeyword(@Param("keyword") String keyword, @Param("status") String status,
			@Param("compareDate") LocalDateTime compareDate);

}
