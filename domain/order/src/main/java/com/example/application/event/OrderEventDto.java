package com.example.application.event;

import lombok.Getter;

@Getter
public class OrderEventDto {

    private final String orderCode;

    public OrderEventDto(String orderCode) {
        this.orderCode = orderCode;
    }
}
