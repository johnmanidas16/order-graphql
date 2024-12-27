package com.product.record.order;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document
public class Address{
    String street;
    String city;
    String state;
    String country;
    String postalCode;
    String phoneNumber;
    boolean isDefault;
    AddressType type;
}
