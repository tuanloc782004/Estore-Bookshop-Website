package com.estorebookshop.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.model.Address;
import com.estorebookshop.model.User;
import com.estorebookshop.service.AddressService;
import com.estorebookshop.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/user/address")
public class MyAddressController {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@RequestMapping("")
	public String adress(Model model, RedirectAttributes redirectAttributes) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = this.userService.findByUsername(username);

		List<Address> addresses = this.addressService.findByUser(user);

		if (addresses != null) {
			model.addAttribute("addresses", addresses);
			ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            String addressesJson = objectMapper.writeValueAsString(addresses);
	            model.addAttribute("addressesJson", addressesJson);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		} else {
			redirectAttributes.addFlashAttribute("error", "Addrress not found!");
		}

		return "user/address";
	}

	@PostMapping("/add")
	public String create(@RequestParam String address, @RequestParam String city, @RequestParam String zipCode,
			@RequestParam String country, RedirectAttributes redirectAttributes) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof CustomUserDetails) {
			username = ((CustomUserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		User user = this.userService.findByUsername(username);

		Address addressOb = new Address();
		addressOb.setCity(city);
		addressOb.setCountry(country);
		addressOb.setZipCode(zipCode);
		addressOb.setAddress(address);
		addressOb.setUser(user);

		try {
			this.addressService.save(addressOb);
			redirectAttributes.addFlashAttribute("message", "Address added successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error adding address: " + e.getMessage());
		}
		return "redirect:/user/address";

	}

	@PostMapping("/delete")
	public String delete(@RequestParam("addressId") Long addressId, RedirectAttributes redirectAttributes) {
		
		try {
			this.addressService.deleteById(addressId);
			redirectAttributes.addFlashAttribute("message", "Address deleted successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error deleting address: " + e.getMessage());
		}
		
		return "redirect:/user/address";
	}
	
	@PostMapping("/edit")
	public String update(@RequestParam Long addressId, @RequestParam String address, @RequestParam String city, @RequestParam String zipCode,
			@RequestParam String country, RedirectAttributes redirectAttributes) {

		Address addressOb = this.addressService.findById(addressId);
		addressOb.setCity(city);
		addressOb.setCountry(country);
		addressOb.setZipCode(zipCode);
		addressOb.setAddress(address);

		try {
			this.addressService.save(addressOb);
			redirectAttributes.addFlashAttribute("message", "Address updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error updating address: " + e.getMessage());
		}
		return "redirect:/user/address";

	}

}
