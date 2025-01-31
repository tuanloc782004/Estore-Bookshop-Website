package com.estorebookshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/login", "/register", "/book-detail/**", "/search/**", "/contact").permitAll()		// Cho phép truy cập không cần đăng nhập đến trang chủ
				.requestMatchers("/admin/**").hasAuthority("ADMIN")				
				.requestMatchers("/user/**").hasAuthority("USER")				
				.anyRequest().authenticated()									// Các yêu cầu khác cần xác thực
			)
			.formLogin((form) -> form
				.loginPage("/login")											// Trang đăng nhập tùy chỉnh
				.loginProcessingUrl("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new CustomAuthenticationSuccessHandler())
			)
			.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true"));

		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		
		return (web)->web.ignoring().requestMatchers("/fe/**", "/assets/**", "/upload-dir/**");
		
	}
	
}
