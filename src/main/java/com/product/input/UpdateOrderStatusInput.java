package com.product.input;

import com.product.record.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusInput {
    private String orderId;
    private OrderStatus status;
    private String comment;
}
