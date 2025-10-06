package com.myproject.microservices.orderservice.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.myproject.microservices.orderservice.Dto.OrderRequestDto;
import com.myproject.microservices.orderservice.Event.OrderPlacedEvent;
import com.myproject.microservices.orderservice.model.Order;
import com.myproject.microservices.orderservice.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepo;
    private final WebClient inventoryClient;
    private final io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry cbRegistry;
    private final io.github.resilience4j.retry.RetryRegistry retryRegistry;
    private final KafkaTemplate<String , OrderPlacedEvent> kafkaTemplate;

    public Mono<Order> placeOrder(OrderRequestDto orderRequest) {
        CircuitBreaker cb = cbRegistry.circuitBreaker("inventory");
        Retry retry = retryRegistry.retry("inventory");

        return inventoryClient.get()
                .uri("?skuCode={skuCode}&quantity={quantity}",
                        orderRequest.getSkuCode(), orderRequest.getQuantity())
                .retrieve()
                .bodyToMono(Boolean.class)
                // apply resilience operators
                .transformDeferred(CircuitBreakerOperator.of(cb))
                .transformDeferred(RetryOperator.of(retry))
                .flatMap(inStock -> {
                    if (!inStock) {
                        log.warn("Product {} is out of stock", orderRequest.getSkuCode());
                        return Mono.just(Order.builder()
                                .orderNumber(UUID.randomUUID().toString())
                                .skuCode(orderRequest.getSkuCode())
                                .price(orderRequest.getPrice())
                                .quantity(orderRequest.getQuantity())
                                .build());
                    }

                    Order order = Order.builder()
                            .orderNumber(UUID.randomUUID().toString())
                            .skuCode(orderRequest.getSkuCode())
                            .price(orderRequest.getPrice())
                            .quantity(orderRequest.getQuantity())
                            .build();

                    return orderRepo.save(order)
                    		.doOnSuccess(savedOrder -> {
                                OrderPlacedEvent event = new OrderPlacedEvent(
                                        savedOrder.getOrderNumber(),
                                        orderRequest.getUserDetails()
                                );
                                kafkaTemplate.send("order-placed", event);
                            });
                })
                // fallback if circuit breaker opens or retry exhausts
                .onErrorResume(ex -> inventoryFallback(orderRequest, ex));
                
    }

    private Mono<Order> inventoryFallback(OrderRequestDto orderRequest, Throwable ex) {
        log.error("Inventory service unavailable, triggering fallback", ex);
        return Mono.just(Order.builder()
                .orderNumber("FALLBACK-" + UUID.randomUUID())
                .skuCode(orderRequest.getSkuCode())
                .price(orderRequest.getPrice())
                .quantity(orderRequest.getQuantity())
                .build());
    }
}
