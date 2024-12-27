package com.product.record.order;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ShippingDetails {
    String trackingNumber;
    String carrier;
    ShippingPriority priority;
    BigDecimal shippingCost;
    LocalDateTime estimatedDeliveryDate;
    LocalDateTime actualDeliveryDate;
    Address shippingAddress;
    List<ShipmentEvent> shipmentHistory;
}
