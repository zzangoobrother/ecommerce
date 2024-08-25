package com.example.listener.dto;

import lombok.Getter;

@Getter
public class OrderEventDto {

    private final String orderCode;
    private int count;

    public OrderEventDto(String orderCode, int count) {
        this.orderCode = orderCode;
        this.count = count;
    }

    public void increasedCount() {
        count++;
    }
}
