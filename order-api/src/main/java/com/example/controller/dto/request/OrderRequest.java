package com.example.controller.dto.request;

public record OrderRequest(
        Long productId,
        int quantity
) {
}
