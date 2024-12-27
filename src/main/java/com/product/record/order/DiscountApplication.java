package com.product.record.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountApplication {
    String discountId;
    String discountCode;
    String description;
    BigDecimal amount;
    DiscountType type;
}
