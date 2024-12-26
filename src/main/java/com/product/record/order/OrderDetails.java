package com.product.record.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collation = "orders")
public record OrderDetails(
        @Id String id,
        String orderNumber,
        String customerId,
        String storeId,
        OrderStatus status,
        LocalDateTime orderDate,
        LocalDateTime lastModifiedDate,
        String lastModifiedBy,

        List<OrderItem> items,
        PriceBreakdown priceBreakdown,
        PaymentDetails paymentDetails,
        ShippingDetails shippingDetails,

        Address billingAddress,
        String customerNotes,
        Map<String, String> metadata,

        @Version Long version,

        // Audit fields
        LocalDateTime createdAt,
        String createdBy,
        List<OrderStatusHistory> statusHistory,
        List<OrderNote> internalNotes
)
{ }
