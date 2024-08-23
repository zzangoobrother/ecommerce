package com.example.listener.dto;

public record ProductDecreaseRequest(
        Long productId,
        int quantity
) {
}
