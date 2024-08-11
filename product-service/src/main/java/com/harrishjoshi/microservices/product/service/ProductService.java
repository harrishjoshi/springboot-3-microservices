package com.harrishjoshi.microservices.product.service;

import com.harrishjoshi.microservices.product.dto.ProductRequest;
import com.harrishjoshi.microservices.product.dto.ProductResponse;
import com.harrishjoshi.microservices.product.model.Product;
import com.harrishjoshi.microservices.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        var product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product created successfully");

        return new ProductResponse(
                product.getId(), product.getName(), product.getDescription(), product.getSkuCode(), product.getPrice()
        );
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product ->
                        new ProductResponse(
                                product.getId(), product.getName(), product.getDescription(), product.getSkuCode(),
                                product.getPrice())
                )
                .toList();
    }
}
