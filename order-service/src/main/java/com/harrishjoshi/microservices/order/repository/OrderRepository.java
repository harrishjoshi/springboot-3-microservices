package com.harrishjoshi.microservices.order.repository;

import com.harrishjoshi.microservices.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
