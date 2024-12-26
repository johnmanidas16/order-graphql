package com.product.record.order;

import java.time.LocalDateTime;

public record ShipmentEvent(
        String location,
        String description,
        LocalDateTime timestamp
)
{ }
