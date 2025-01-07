package com.estorebookshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.estorebookshop.model.Address;

public interface AddressService {

	public Address findById(Long id);

	public Address save(Address address);

	public void deleteById(Long id);

	public List<Address> findByKeyword(String keyword);

	public Page<Address> findAll(Integer pageno);

	public Page<Address> findByKeyword(String keyword, Integer pageNo);
	
}
