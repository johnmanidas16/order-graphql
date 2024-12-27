package com.product.record.order;

import java.math.BigDecimal;
import java.util.List;

public class PriceBreakdown {
    BigDecimal subtotal;
    BigDecimal tax;
    BigDecimal shippingCost;
    BigDecimal discount;
    List<DiscountApplication> appliedDiscounts;
    BigDecimal total;
}
