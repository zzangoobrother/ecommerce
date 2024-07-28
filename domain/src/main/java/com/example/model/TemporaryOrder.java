package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class TemporaryOrder {
    private Long id;
    private Long memberId;
    private Long productId;
    private String ordersCode;
    private int quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public TemporaryOrder(Long id, Long memberId, Long productId, String ordersCode, int quantity, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.ordersCode = ordersCode;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
