package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.OrderDetail;
import com.estorebookshop.repository.OrderDetailRepository;
import com.estorebookshop.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Override
	public List<OrderDetail> findAllByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		return orderDetailRepository.findAll().stream().filter(orderDetail -> orderDetail.getOrder().getId().equals(orderId))
				.toList();
	}

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		return this.orderDetailRepository.save(orderDetail);
	}

}
