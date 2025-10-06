package com.myproject.microservices.product.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponseDto {
	private String id;
	private String name;
	private String description;
	private BigDecimal price;
}
