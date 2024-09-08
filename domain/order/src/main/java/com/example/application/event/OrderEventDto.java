package com.example.application.event;

public record OrderEventDto(
        String orderCode,
        String token
) {
}
