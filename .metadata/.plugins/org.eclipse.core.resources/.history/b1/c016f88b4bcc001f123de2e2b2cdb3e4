package com.myscp.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		// Kiểm tra vai trò người dùng
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

		if (isAdmin) {
			response.sendRedirect("/admin"); // Redirect to admin if user is ADMIN
		} else {
			response.sendRedirect("/"); // Redirect to home if user is USER or other roles
		}
	}
}
