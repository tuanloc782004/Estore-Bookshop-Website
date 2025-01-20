package com.estorebookshop.config.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.estorebookshop.config.model.CustomUserDetails;
import com.estorebookshop.model.User;
import com.estorebookshop.service.UserService;

@Service
@ControllerAdvice
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
	
	@ModelAttribute("avatarUrl")
    public String getAvatarUrl() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
            !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            return user != null ? user.getAvatarUrl() : null;
        }
        return null;
    }

}
