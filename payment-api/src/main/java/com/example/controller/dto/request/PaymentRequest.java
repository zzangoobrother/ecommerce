package com.example.controller.dto.request;

import java.math.BigDecimal;

public record PaymentRequest(
        String orderCode,
        BigDecimal price
) {
}
