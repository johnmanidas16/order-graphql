package com.product.record.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document
public class OrderStatusHistory {
    OrderStatus status;
    String comment;
    LocalDateTime timestamp;
    String updatedBy;
}
