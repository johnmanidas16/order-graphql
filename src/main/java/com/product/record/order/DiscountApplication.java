package com.product.record.order;

import java.math.BigDecimal;

public record DiscountApplication(
        String discountId,
        String discountCode,
        String description,
        BigDecimal amount,
        DiscountType type
) { }
