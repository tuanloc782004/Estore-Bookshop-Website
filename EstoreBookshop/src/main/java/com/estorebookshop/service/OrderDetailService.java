package com.estorebookshop.service;

import java.util.List;

import com.estorebookshop.model.OrderDetail;

public interface OrderDetailService {

	public List<OrderDetail> findAllByOrderId(Long orderId);
	
}
