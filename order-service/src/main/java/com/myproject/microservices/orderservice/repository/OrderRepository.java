package com.myproject.microservices.orderservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.myproject.microservices.orderservice.model.Order;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long>{

}
