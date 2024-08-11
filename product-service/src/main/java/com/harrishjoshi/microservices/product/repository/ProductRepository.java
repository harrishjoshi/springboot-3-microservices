package com.harrishjoshi.microservices.product.repository;

import com.harrishjoshi.microservices.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
