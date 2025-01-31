package com.estorebookshop.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Review;
import com.estorebookshop.repository.ReviewRepository;
import com.estorebookshop.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public List<Review> findByKeyword(String checked, LocalDateTime startDate, LocalDateTime endDate) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findByKeyword(checked, startDate, endDate);
	}

	@Override
	public Page<Review> findAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		return this.reviewRepository.findAll(pageable);
	}

	@Override
	public Page<Review> findByKeyword(String checked, LocalDateTime startDate, LocalDateTime endDate,
			Integer pageNo) {
		// TODO Auto-generated method stub
		List<Review> list = this.reviewRepository.findByKeyword(checked, startDate, endDate);
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		
		return new PageImpl<Review>(list, pageable, this.reviewRepository.findByKeyword(checked, startDate, endDate).size());
	}

	@Override
	public void setStatus(Long reviewId) {
		// TODO Auto-generated method stub
		Review review = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found!"));
        review.setChecked("Checked");
        this.reviewRepository.save(review);
	}

	@Override
	public void deleteComment(Long reviewId) {
		// TODO Auto-generated method stub
		Review review = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found!"));
		review.setComment("The comment has been deleted for violating community standards !!!");
        review.setChecked("Checked");
        this.reviewRepository.save(review);
	}

	@Override
	public Review findById(Long id) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findById(id).orElse(null);
	}

	@Override
	public List<Review> findByBookIdSortByCreatedAt(Long bookId) {
		// TODO Auto-generated method stub
		return this.reviewRepository.findByBookIdSortByCreatedAt(bookId);
	}

	@Override
	public Review save(Review review) {
		// TODO Auto-generated method stub
		return this.reviewRepository.save(review);
	}

}
