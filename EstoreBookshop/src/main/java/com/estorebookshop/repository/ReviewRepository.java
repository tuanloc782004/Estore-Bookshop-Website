package com.estorebookshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.estorebookshop.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("SELECT r FROM Review r WHERE " + "(:checked = 'all' OR r.checked = :checked) AND "
			+ "(r.createdAt BETWEEN :startDate AND :endDate)")
	List<Review> findByKeyword(@Param("checked") String checked, @Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);
	
	@Query("SELECT r FROM Review r WHERE r.book.id = :bookId AND r.checked = 'Checked' ORDER BY r.createdAt DESC")
    List<Review> findByBookIdSortByCreatedAt(Long bookId);
	
}
