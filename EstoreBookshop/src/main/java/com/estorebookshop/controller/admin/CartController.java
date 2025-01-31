package com.estorebookshop.controller.admin;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.service.EmailService;
import com.estorebookshop.model.Cart;
import com.estorebookshop.service.CartItemService;
import com.estorebookshop.service.CartService;

@Controller
@RequestMapping("/admin/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private EmailService emailService;

	@RequestMapping("")
	public String cart(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "status", required = false, defaultValue = "all") String status,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Cart> list = this.cartService.findAll(pageNo);

		LocalDateTime compareDate = LocalDateTime.now().minusDays(90);

		if (keyword != null) {
			list = this.cartService.findByKeyword(keyword, status, compareDate, pageNo);
		}

		model.addAttribute("keyword", keyword);
		model.addAttribute("status", status);
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("compareDate", compareDate);
		model.addAttribute("carts", list);
		
		model.addAttribute("current", "cart");
		
		return "admin/cart/cart";
	}

	@GetMapping("/delete/{id}")
	@Transactional
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			Cart cart = cartService.findById(id);

			if (cart != null && cart.getUser() != null) {
				String email = cart.getUser().getEmail();
				String username = cart.getUser().getUsername();

				cartItemService.deleteByCartId(id);
				cartService.deleteById(id);

				try {
					emailService.sendEmail(email, "Notification from Estore Bookshop", String.format(
							"Dear %s,<br><br>Your cart has been deleted by the administrator due to inactivity for the past 90 days. If you have any questions or concerns, feel free to contact us at <a href='mailto:jobhere.22t.nhat1@gmail.com'>jobhere.22t.nhat1@gmail.com</a>.<br><br>Thank you for your understanding.<br><br>Best regards,<br>Estore Bookshop",
							username));
				} catch (Exception e) {
					redirectAttributes.addFlashAttribute("error",
							"Error occurred while sending notification email: " + e.getMessage());
					return "redirect:/admin/cart";
				}

				redirectAttributes.addFlashAttribute("message",
						"Cart deleted and email notification sent successfully.");
			} else {
				redirectAttributes.addFlashAttribute("error", "Cart not found or user not found.");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error occurred while deleting the cart: " + e.getMessage());
		}

		return "redirect:/admin/cart";
	}
}
