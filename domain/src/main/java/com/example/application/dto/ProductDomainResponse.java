package com.example.application.dto;

import com.example.model.Product;

import java.math.BigDecimal;

public record ProductDomainResponse(
        Long productId,
        String name,
        BigDecimal price,
        int quantity
) {

    public static ProductDomainResponse toProductDomainResponse(Product product) {
        return new ProductDomainResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity());
    }
}
