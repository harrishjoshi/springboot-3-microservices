package com.harrishjoshi.microservices.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_inventory")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity;
}
