package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Order {
    private Long id;
    private Long memberId;
    private String ordersCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Order(Long id, Long memberId, String ordersCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.ordersCode = ordersCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
