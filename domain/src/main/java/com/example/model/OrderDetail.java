package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class OrderDetail {

    private Long id;
    private Long ordersId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public OrderDetail(Long id, Long ordersId, Long productId, int quantity, BigDecimal price, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public enum OrderStatus {
        COMPLETE, CANCEL
    }
}
