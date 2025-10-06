package com.myproject.microservices.orderservice.Event;

import com.myproject.microservices.orderservice.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {
	private String orderNumber;
	private User user;
}