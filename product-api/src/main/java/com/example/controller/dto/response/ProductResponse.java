package com.example.controller.dto.response;

import com.example.application.dto.ProductDomainResponse;

import java.math.BigDecimal;

public record ProductResponse(
        Long productId,
        String name,
        BigDecimal price,
        int quantity
) {

    public static ProductResponse toProductResponse(ProductDomainResponse response) {
        return new ProductResponse(response.productId(), response.name(), response.price(), response.quantity());
    }
}
