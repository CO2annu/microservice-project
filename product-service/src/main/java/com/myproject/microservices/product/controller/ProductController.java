package com.myproject.microservices.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.microservices.product.dto.ProductRequestDto;
import com.myproject.microservices.product.dto.ProductResponseDto;
import com.myproject.microservices.product.model.Product;
import com.myproject.microservices.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService productservice;
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> createProduct(@RequestBody ProductRequestDto product){
		return productservice.createProduct(product);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Flux<ProductResponseDto> getAllProducts(){
		return productservice.getAllProducts();
	}
}
