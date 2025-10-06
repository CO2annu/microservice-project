package com.myproject.microservices.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.microservices.orderservice.Dto.OrderRequestDto;
import com.myproject.microservices.orderservice.model.Order;
import com.myproject.microservices.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order")
@Slf4j
@RequiredArgsConstructor
public class orderController {
	
	private final OrderService orderservice;
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Order> placeOrder(@RequestBody OrderRequestDto orderRequest) {
	    return orderservice.placeOrder(orderRequest);
	}

}
