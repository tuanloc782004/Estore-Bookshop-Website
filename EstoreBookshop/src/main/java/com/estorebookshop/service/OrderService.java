package com.estorebookshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Order;

public interface OrderService {

	public List<Order> findByKeyword(String username, String status, LocalDateTime startDate, LocalDateTime endDate);
	
	public Page<Order> findAll(Integer pageNo);
	
	public Page<Order> findByKeyword(String username, String status, LocalDateTime startDate, LocalDateTime endDate, Integer pageNo);

    void setStatus(Long orderId, String status);
    
    public Order findById(Long id);
    
}
