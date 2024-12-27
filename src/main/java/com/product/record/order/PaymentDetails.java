package com.product.record.order;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Document
public class PaymentDetails {
    String transactionId;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    BigDecimal amount;
    String currency;
    String gatewayResponse;
    LocalDateTime paymentDate;
    Map<String, String> additionalDetails;
}
