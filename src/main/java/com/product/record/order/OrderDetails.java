package com.product.record.order;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Builder
@Document(collection = "orders")
@Data
public class OrderDetails {
        @MongoId(FieldType.OBJECT_ID)
        String id;
        String orderNumber;
        String customerId;
        String storeId;
        OrderStatus status;
        LocalDateTime orderDate;
        LocalDateTime lastModifiedDate;
        String lastModifiedBy;

        List<OrderItem> items;
        PriceBreakdown priceBreakdown;
        PaymentDetails paymentDetails;
        ShippingDetails shippingDetails;

        Address billingAddress;
        String customerNotes;
        Map<String, String> metadata;

        @Version
        Long version;

        LocalDateTime createdAt;
        String createdBy;
        List<OrderStatusHistory> statusHistory;
        List<OrderNote> internalNotes;
}
