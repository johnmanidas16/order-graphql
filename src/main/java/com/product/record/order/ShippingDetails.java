package com.product.record.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ShippingDetails(
        String trackingNumber,
        String carrier,
        ShippingPriority priority,
        BigDecimal shippingCost,
        LocalDateTime estimatedDeliveryDate,
        LocalDateTime actualDeliveryDate,
        Address shippingAddress,
        List<ShipmentEvent> shipmentHistory
) { }
