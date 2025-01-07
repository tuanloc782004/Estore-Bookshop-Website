package com.estorebookshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.estorebookshop.model.Address;
import com.estorebookshop.service.AddressService;

@Controller
@RequestMapping("/admin/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@RequestMapping("")
	public String address(Model model, @Param("keyword") String keyword,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

		Page<Address> list = this.addressService.findAll(pageNo);

		if (keyword != null) {
			list = this.addressService.findByKeyword(keyword, pageNo);
			model.addAttribute("keyword", keyword);
		}

		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", pageNo);

		model.addAttribute("addresses", list);
		return "admin/address/address";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model, @PathVariable("id") Long id) {
		Address address = this.addressService.findById(id);
		model.addAttribute("address1", address);
		return "admin/address/address-form";
	}
	
	@PostMapping("/edit")
	public String update(@ModelAttribute("address1") Address address) {
		this.addressService.save(address);
		return "redirect:/admin/address";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		this.addressService.deleteById(id);
		return "redirect:/admin/address";
	}

}
