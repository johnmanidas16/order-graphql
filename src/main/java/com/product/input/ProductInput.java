package com.product.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInput {
    private String id;
    private String description;
    private String name;
    BigDecimal price;
    private int stockQuantity;
}
