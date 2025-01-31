package com.estorebookshop.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Address;
import com.estorebookshop.model.User;
import com.estorebookshop.repository.AddressRepository;
import com.estorebookshop.repository.UserRepository;
import com.estorebookshop.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Address findById(Long id) {
		// TODO Auto-generated method stub
		return this.addressRepository.findById(id).orElse(null);
	}

	@Override
	public Address save(Address address) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findByUsername(address.getUser().getUsername());
		address.setUser(user);
		return this.addressRepository.save(address);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		this.addressRepository.deleteById(id);
	}

	@Override
	public List<Address> findByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return this.addressRepository.findByKeyword(keyword);
	}

	@Override
	public Page<Address> findAll(Integer pageno) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageno - 1, 8);
		return this.addressRepository.findAll(pageable);
	}

	@Override
	public Page<Address> findByKeyword(String keyword, Integer pageNo) {
		// TODO Auto-generated method stub
		List<Address> list = this.addressRepository.findByKeyword(keyword);
		Pageable pageable = PageRequest.of(pageNo - 1, 8);
		Integer start = (int) pageable.getOffset();
		Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
				: pageable.getOffset() + pageable.getPageSize());
		list = list.subList(start, end);
		return new PageImpl<Address>(list, pageable, this.findByKeyword(keyword).size());
	}

	@Override
	public List<Address> findByUser(User user) {
		// TODO Auto-generated method stub
		return this.addressRepository.findByUser(user);
	}
	
}
