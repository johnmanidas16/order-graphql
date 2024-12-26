package com.product.record.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Product(
    @Id String id,
    String name, 
    String description,
    BigDecimal price,
    Integer stockQuantity,
    LocalDateTime createdAt
) {
    public Product {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}