package com.product.record.order;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public record OrderStatusHistory(
        OrderStatus status,
        String comment,
        LocalDateTime timestamp,
        String updatedBy
)
{ }
