package com.myproject.microservices.product.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductRequestDto {
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
}
