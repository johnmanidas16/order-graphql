package com.product.record.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record OrderItem(
        String productId,
        String productName,
        String sku,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal discount,
        Map<String, String> productAttributes,
        List<String> customizations
)
{ }
