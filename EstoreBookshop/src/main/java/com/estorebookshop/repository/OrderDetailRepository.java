package com.estorebookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estorebookshop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
