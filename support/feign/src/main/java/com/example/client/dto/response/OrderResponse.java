package com.example.client.dto.response;

import java.math.BigDecimal;

public record OrderResponse(
        Long productId,
        int quantity,
        BigDecimal price
) {
}
