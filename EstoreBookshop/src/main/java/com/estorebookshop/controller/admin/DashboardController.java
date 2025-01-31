package com.estorebookshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.estorebookshop.service.BookService;
import com.estorebookshop.service.OrderService;
import com.estorebookshop.service.UserService;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController {

	@Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("")
    public String showDashboard(Model model) {
        // Đếm số lượng user, book, order
        long userCount = userService.countUsers();
        long bookCount = bookService.countBooks();
        long orderCount = orderService.countOrders();

        model.addAttribute("userCount", userCount);
        model.addAttribute("bookCount", bookCount);
        model.addAttribute("orderCount", orderCount);
        
        model.addAttribute("current", "dashboard");

        return "admin/dashboard";
    }
}
