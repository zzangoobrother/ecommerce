package com.example.client.dto.request;

import java.math.BigDecimal;

public record PaymentRequest(
        String orderCode,
        BigDecimal price
) {
}
