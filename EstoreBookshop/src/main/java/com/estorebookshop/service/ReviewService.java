package com.estorebookshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Review;

public interface ReviewService {

	public List<Review> findByKeyword(String checked, LocalDateTime startDate, LocalDateTime endDate);

	public Page<Review> findAll(Integer pageNo);

	public Page<Review> findByKeyword(String checked, LocalDateTime startDate, LocalDateTime endDate,
			Integer pageNo);

	void setStatus(Long reviewId);
	
	void deleteComment(Long reviewId);
	
	public Review findById(Long id);

}
