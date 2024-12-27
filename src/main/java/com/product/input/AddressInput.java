package com.product.input;

import com.product.record.order.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressInput {
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private boolean isDefault;
    private AddressType type;
}
