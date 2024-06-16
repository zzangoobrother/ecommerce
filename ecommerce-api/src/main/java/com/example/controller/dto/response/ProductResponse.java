package com.example.controller.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long productId,
        String name,
        BigDecimal price,
        int quantity
) {
}
