package com.example.application.event;

import lombok.Getter;

@Getter
public class OrderEvent {

    private final String orderCode;

    public OrderEvent(String orderCode) {
        this.orderCode = orderCode;
    }
}
