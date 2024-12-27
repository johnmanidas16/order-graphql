package com.product.input;

import com.product.record.order.PaymentMethod;
import com.product.record.order.ShippingPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderInput {
    private String customerId;
    private String storeId;
    private List<OrderItemInput> items;
    private AddressInput shippingAddress;
    private AddressInput billingAddress;
    private PaymentMethod paymentMethod;
    private ShippingPriority shippingPriority;
    private String customerNotes;
}
