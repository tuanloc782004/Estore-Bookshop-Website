package com.estorebookshop.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estorebookshop.model.Role;
import com.estorebookshop.repository.RoleRepository;
import com.estorebookshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository repository;
	
	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		return this.repository.findById(id).orElse(null);
	}

}
