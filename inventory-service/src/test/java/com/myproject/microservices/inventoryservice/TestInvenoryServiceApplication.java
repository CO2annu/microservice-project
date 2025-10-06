package com.myproject.microservices.inventoryservice;

import org.springframework.boot.SpringApplication;

public class TestInvenoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(InvenoryServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
