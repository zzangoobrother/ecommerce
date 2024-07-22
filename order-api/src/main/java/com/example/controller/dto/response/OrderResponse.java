package com.example.controller.dto.response;

import java.math.BigDecimal;

public record OrderResponse(
        Long productId,
        int quantity,
        BigDecimal price
) {
}
