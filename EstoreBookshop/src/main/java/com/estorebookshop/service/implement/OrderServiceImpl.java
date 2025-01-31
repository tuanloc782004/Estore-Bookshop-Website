package com.estorebookshop.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Order;
import com.estorebookshop.model.User;
import com.estorebookshop.repository.OrderRepository;
import com.estorebookshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> findByKeyword(String username, String status, LocalDateTime startDate, LocalDateTime endDate) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByKeyword(username, status, startDate, endDate);
	}

	@Override
	public Page<Order> findAll(Integer pageNo) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		return this.orderRepository.findAll(pageable);
	}

	@Override
	public Page<Order> findByKeyword(String username, String status, LocalDateTime startDate, LocalDateTime endDate,
			Integer pageNo) {
		// TODO Auto-generated method stub
		List<Order> list = this.orderRepository.findByKeyword(username, status, startDate, endDate);
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		
		return new PageImpl<Order>(list, pageable, this.orderRepository.findByKeyword(username, status, startDate, endDate).size());
	}

	@Override
	public void setStatus(Long orderId, String status) {
		// TODO Auto-generated method stub
		Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
        order.setStatus(status);
        this.orderRepository.save(order);
	}

	@Override
	public Order findById(Long id) {
		// TODO Auto-generated method stub
		return this.orderRepository.findById(id).orElse(null);
	}

	@Override
	public List<Order> findByUser(User user) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByUser(user);
	}

	@Override
	public Order save(Order order) {
		// TODO Auto-generated method stub
		return this.orderRepository.save(order);
	}

	@Override
	public long countOrders() {
		// TODO Auto-generated method stub
		return this.orderRepository.count();
	}
	
}
