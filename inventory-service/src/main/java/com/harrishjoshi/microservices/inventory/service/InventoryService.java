package com.harrishjoshi.microservices.inventory.service;

import com.harrishjoshi.microservices.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public boolean isInStock(String skuCode, Integer quantity) {
        log.info("Request to check stock for skuCode {}, with quantity {}", skuCode, quantity);
        var isInStock = inventoryRepository.checkSkuCodeInStock(skuCode, quantity);
        log.info("Product with skuCode {}, and quantity {}, is in stock - {}", skuCode, quantity, isInStock);

        return isInStock;
    }
}
