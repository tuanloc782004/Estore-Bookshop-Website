package com.estorebookshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Configuration
public class PayPalConfig {
	private String clientId = "AVlSNfN53hPjhLGPJnV5PRdZXBOWwmUVLnlt9yFZXkmXZTBad7d-MhAKnr5XcFuZ5toDtRjjwNB4iqaG"; // Thay bằng Client ID
	private String clientSecret = "ELQFI8ddlP7Qe7SHU25IpO21r6dkCIMn1b3IuYhLBvvJLt8_oRjhvrObkJYX3WBgVVCqFXfxVJrh-zhc"; // Thay bằng Client Secret
	private String mode = "sandbox"; // Hoặc "live" nếu triển khai thực tế

	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(clientId, clientSecret, mode);
		return context;
	}
}