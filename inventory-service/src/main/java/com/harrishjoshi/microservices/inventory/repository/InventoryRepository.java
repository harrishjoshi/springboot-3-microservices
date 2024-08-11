package com.harrishjoshi.microservices.inventory.repository;

import com.harrishjoshi.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END 
            FROM Inventory i 
            WHERE i.skuCode = ?1 AND i.quantity >= ?2
            """)
    boolean checkSkuCodeInStock(String skuCode, Integer quantity);
}
