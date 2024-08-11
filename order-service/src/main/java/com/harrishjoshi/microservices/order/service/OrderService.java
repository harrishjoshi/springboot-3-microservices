package com.harrishjoshi.microservices.order.service;

import com.harrishjoshi.microservices.order.dto.OrderRequest;
import com.harrishjoshi.microservices.order.model.Order;
import com.harrishjoshi.microservices.order.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderRequest orderRequest) {
        var order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        order.setPrice(orderRequest.price());

        orderRepository.save(order);
    }
}
