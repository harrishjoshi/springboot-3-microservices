package com.harrishjoshi.microservices.order.service;

import com.harrishjoshi.microservices.order.config.InventoryClient;
import com.harrishjoshi.microservices.order.dto.OrderRequest;
import com.harrishjoshi.microservices.order.model.Order;
import com.harrishjoshi.microservices.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    public void placeOrder(OrderRequest orderRequest) {
        var isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (!isInStock) {
            log.error("Product with skuCode {} is out of stock", orderRequest.skuCode());
            throw new RuntimeException("Product is out of stock");
        }

        var order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        order.setPrice(orderRequest.price());

        orderRepository.save(order);
    }
}
