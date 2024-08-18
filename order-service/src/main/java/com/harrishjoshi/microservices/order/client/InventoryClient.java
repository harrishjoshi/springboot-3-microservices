package com.harrishjoshi.microservices.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    @GetExchange("/api/v1/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "inStockFallback")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean inStockFallback(String skuCode, Integer quantity, Throwable throwable) {
        log.info("Failed to check inventory service for skuCode {} and quantity {}; failure reason: {}", skuCode, quantity,
                throwable.getMessage());
        return false;
    }
}
