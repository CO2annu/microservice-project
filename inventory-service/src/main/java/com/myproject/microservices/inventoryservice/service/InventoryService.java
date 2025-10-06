package com.myproject.microservices.inventoryservice.service;

import org.springframework.stereotype.Service;

import com.myproject.microservices.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
	private final InventoryRepository inventoryRepo;
	
	public Mono<Boolean> isInStock(String skuCode, Integer quantity){
		return inventoryRepo.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
	} 
}
