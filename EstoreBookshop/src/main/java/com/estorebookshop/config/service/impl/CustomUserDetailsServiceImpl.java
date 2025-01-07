package com.estorebookshop.config.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.model.User;
import com.estorebookshop.service.UserService;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userService.findByUsername(username);
			
		if(user == null) {
			throw new UsernameNotFoundException("Not found " + username);
		}
		
		Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
		
		grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
		
		
		return new CustomUserDetails(user, grantedAuthoritySet);
		
	}

}
