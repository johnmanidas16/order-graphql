package com.product.record.order;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Address(
        String street,
        String city,
        String state,
        String country,
        String postalCode,
        String phoneNumber,
        boolean isDefault,
        AddressType type
) { }
