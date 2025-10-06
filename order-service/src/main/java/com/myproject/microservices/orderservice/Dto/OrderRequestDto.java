package com.myproject.microservices.orderservice.Dto;

import java.math.BigDecimal;

import com.myproject.microservices.orderservice.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
	private Long id;
	private String orderNumber;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
	private User userDetails;
}
