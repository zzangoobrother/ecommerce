package com.example.application.dto;

public record OrderRequestDto(
        Long productId,
        int quantity
) {
}
