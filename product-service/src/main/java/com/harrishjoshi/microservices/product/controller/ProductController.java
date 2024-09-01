package com.harrishjoshi.microservices.product.controller;

import com.harrishjoshi.microservices.product.dto.ProductRequest;
import com.harrishjoshi.microservices.product.dto.ProductResponse;
import com.harrishjoshi.microservices.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        log.info("Get all products");
        return productService.getAllProducts();
    }
}
