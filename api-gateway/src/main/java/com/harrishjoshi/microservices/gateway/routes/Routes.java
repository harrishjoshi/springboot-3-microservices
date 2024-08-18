package com.harrishjoshi.microservices.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Configuration
public class Routes {

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${order.service.url}")
    private String orderServiceUrl;

    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return GatewayRouterFunctions.route("product-service")
                .route(RequestPredicates.path("/api/v1/product/**"), HandlerFunctions.http(productServiceUrl))
                .filter(circuitBreaker("productServiceCircuitBreaker", URI.create("forward:/fallback")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("product-service-swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v1/api-docs"), HandlerFunctions.http(productServiceUrl))
                .filter(circuitBreaker("productServiceSwaggerCircuitBreaker", URI.create("forward:/fallback")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/v1/order/**"), HandlerFunctions.http(orderServiceUrl))
                .filter(circuitBreaker("orderServiceCircuitBreaker", URI.create("forward:/fallback")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("order-service-swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v1/api-docs"), HandlerFunctions.http(orderServiceUrl))
                .filter(circuitBreaker("orderServiceSwaggerCircuitBreaker", URI.create("forward:/fallback")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> inventoryServiceRoute() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/v1/inventory/**"), HandlerFunctions.http(inventoryServiceUrl))
                .filter(circuitBreaker("inventoryServiceCircuitBreaker", URI.create("forward:/fallback")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute() {
        return GatewayRouterFunctions.route("inventory-service-swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v1/api-docs"), HandlerFunctions.http(inventoryServiceUrl))
                .filter(circuitBreaker("inventoryServiceSwaggerCircuitBreaker", URI.create("forward:/fallback")))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallback", request ->
                        ServerResponse.status(SERVICE_UNAVAILABLE).body("Service unavailable, please try again later")
                )
                .build();
    }
}
