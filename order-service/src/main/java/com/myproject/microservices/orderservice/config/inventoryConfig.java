package com.myproject.microservices.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class inventoryConfig{
	
	@Bean
	public WebClient inventoryClient(WebClient.Builder builder) {
		return builder.baseUrl("http://localhost:8082/api/inventory").build();
	}
}
