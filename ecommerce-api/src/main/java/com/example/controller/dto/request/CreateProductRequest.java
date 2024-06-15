package com.example.controller.dto.request;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        BigDecimal price,
        int quantity
) {
}
