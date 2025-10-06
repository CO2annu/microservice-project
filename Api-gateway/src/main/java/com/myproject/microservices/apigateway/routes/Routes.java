package com.myproject.microservices.apigateway.routes;

import java.util.Map;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouteLocator productServiceRoute(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("product_service", r -> r.path("/api/product/**")
            		.filters(f -> f.circuitBreaker(c -> c.setName("productServiceCircuitBreaker").setFallbackUri("forward:/fallbackRoute/GET")))
                .uri("http://localhost:8080"))
            .build();
    }
    @Bean
    public RouteLocator orderServiceRoute(RouteLocatorBuilder builder) {
    	return builder.routes()
    			.route("order-service", r -> r.path("/api/order/**")
    					.filters(f -> f.circuitBreaker(c -> c.setName("orderServiceCircuitBreaker").setFallbackUri("forward:/fallbackRoute/GET")))
    					.uri("http://localhost:8081"))
    			.build();
    			
    }
    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return RouterFunctions.route()
        		.GET("/fallbackRoute/GET", request ->
        			ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
        			.contentType(MediaType.APPLICATION_JSON)
        			.bodyValue(Map.of("message", "Server not Reachable"))
        		)
        		.POST("/fallbackRoute/GET", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
        				.contentType(MediaType.APPLICATION_JSON)
        				.bodyValue(Map.of("message", "Server not Reachable"))
        		)
        		.build();
    }
}