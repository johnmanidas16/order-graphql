package com.product.record.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
public class OrderItem {
    String productId;
    String productName;
    String sku;
    Integer quantity;
    BigDecimal unitPrice;
    BigDecimal discount;
    Map<String, String> productAttributes;
    List<String> customizations;
}
