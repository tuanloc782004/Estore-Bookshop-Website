package com.estorebookshop.controller.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.model.Order;
import com.estorebookshop.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping("")
	public String order(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "status", required = false, defaultValue = "All") String status,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, Model model) {

		if (startDate == null) {
			startDate = LocalDate.of(2000, 1, 1);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}

		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

		Page<Order> list = this.orderService.findAll(pageNo);

		if (username != null || status != null || startDateTime != null || endDateTime != null) {
			list = this.orderService.findByKeyword(username, status, startDateTime, endDateTime, pageNo);
		}

		model.addAttribute("username", username);
		model.addAttribute("status", status);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("orders", list);

		return "admin/order/order";
	}

	@GetMapping("/set-status")
	public String setStatus(@RequestParam Long orderId, @RequestParam String status,
			RedirectAttributes redirectAttributes) {
		try {
			orderService.setStatus(orderId, status);
			redirectAttributes.addFlashAttribute("message", "Order status updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to update order status.");
		}
		return "redirect:/admin/order";
	}

}
