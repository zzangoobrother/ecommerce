package com.example.application.listener.dto;

import lombok.Getter;

@Getter
public class OrderEventDto {

    private final String orderCode;
    private final int count;

    public OrderEventDto(String orderCode, int count) {
        this.orderCode = orderCode;
        this.count = count;
    }
}