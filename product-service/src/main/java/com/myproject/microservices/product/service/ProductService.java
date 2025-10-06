package com.myproject.microservices.product.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.microservices.product.dto.ProductRequestDto;
import com.myproject.microservices.product.dto.ProductResponseDto;
import com.myproject.microservices.product.model.Product;
import com.myproject.microservices.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class ProductService {
	
	@Autowired
	private ProductRepository productRepo;
	
	public Mono<Product> createProduct(ProductRequestDto productRequest){
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		
		log.info("Product created successfully");
		return productRepo.save(product);
	}
	
	public Flux<ProductResponseDto> getAllProducts(){
		return productRepo.findAll()
		.map(product ->{
			return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
		});
	}
}
