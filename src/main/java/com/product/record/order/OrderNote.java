package com.product.record.order;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public record OrderNote(
        String note,
        String author,
        LocalDateTime timestamp,
        boolean isCustomerVisible
) { }
