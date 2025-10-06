package com.myproject.microservices.inventoryservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.myproject.microservices.inventoryservice.model.Inventory;

import reactor.core.publisher.Mono;

public interface InventoryRepository extends ReactiveCrudRepository<Inventory, Long>{
	Mono<Boolean> existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
