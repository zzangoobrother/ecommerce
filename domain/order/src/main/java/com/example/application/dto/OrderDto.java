package com.example.application.dto;

import java.math.BigDecimal;

public record OrderDto(
        Long productId,
        int quantity,
        BigDecimal price
) {
}
