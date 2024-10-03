package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Coupon {

    private Long id;
    private String code;
    private int count;
    private LocalDateTime createdAt;

    @Builder
    public Coupon(Long id, String code, int count, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.count = count;
        this.createdAt = createdAt;
    }
}
