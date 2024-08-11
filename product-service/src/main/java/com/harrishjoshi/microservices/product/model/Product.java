package com.harrishjoshi.microservices.product.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "product")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
}
