package com.product.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrderNoteInput {
    private String orderId;
    private String note;
    private boolean isCustomerVisible;
}