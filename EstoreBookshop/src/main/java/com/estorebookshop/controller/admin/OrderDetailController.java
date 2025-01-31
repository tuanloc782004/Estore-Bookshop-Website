package com.estorebookshop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estorebookshop.model.OrderDetail;
import com.estorebookshop.service.OrderDetailService;
import com.estorebookshop.service.OrderService;

@Controller
@RequestMapping("/admin/order-detail")
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/{id}")
	public String orderDetail(@PathVariable("id") Long orderId, Model model) {
		List<OrderDetail> orderDetails = orderDetailService.findAllByOrderId(orderId);

		// Gửi dữ liệu vào view
		model.addAttribute("order", this.orderService.findById(orderId));
		model.addAttribute("orderDetails", orderDetails);
		model.addAttribute("current", "order");
		return "admin/order-detail/order-detail";
	}
	
}
