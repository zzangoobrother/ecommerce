package com.example.application.dto;

import java.math.BigDecimal;

public record PaymentDto(
        String orderCode,
        BigDecimal totalPrice
) {
}
