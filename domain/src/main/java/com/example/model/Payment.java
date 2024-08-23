package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class Payment {
    private Long id;
    private String ordersCode;
    private BigDecimal price;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Payment(Long id, String ordersCode, BigDecimal price, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ordersCode = ordersCode;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void cancel() {
        this.status = Status.CANCEL;
    }

    public enum Status {
        SUCCESS, CANCEL
    }
}
