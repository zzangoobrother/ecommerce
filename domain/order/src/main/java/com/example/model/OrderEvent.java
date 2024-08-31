package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderEvent {
    private Long id;
    private String ordersCode;
    private OrderEventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public OrderEvent(Long id, String ordersCode, OrderEventStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ordersCode = ordersCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void success() {
        this.status = OrderEventStatus.SEND_SUCCESS;
    }
}
