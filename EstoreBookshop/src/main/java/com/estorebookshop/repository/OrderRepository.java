package com.estorebookshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estorebookshop.model.Order;
import com.estorebookshop.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o WHERE " + "(:username IS NULL OR o.user.username LIKE %:username%) AND "
			+ "(:status IS NULL OR :status = 'All' OR o.status = :status) AND "
			+ "(:startDateTime IS NULL OR o.createdAt >= :startDateTime) AND "
			+ "(:endDateTime IS NULL OR o.createdAt <= :endDateTime)")
	List<Order> findByKeyword(@Param("username") String username, @Param("status") String status,
			@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

	List<Order> findByUser(User user);
}
