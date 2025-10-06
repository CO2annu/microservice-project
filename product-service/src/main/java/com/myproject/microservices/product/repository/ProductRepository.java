package com.myproject.microservices.product.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.myproject.microservices.product.dto.ProductRequestDto;
import com.myproject.microservices.product.model.Product;

import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<Product, String>{

	Mono<Product> save(ProductRequestDto product);
	
}
