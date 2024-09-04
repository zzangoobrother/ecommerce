package com.example.client;

import com.example.client.dto.response.OrderResponse;

import java.math.BigDecimal;

public class FakeOrderClient implements OrderClient {
    @Override
    public OrderResponse getBy(String orderCode) {
        return new OrderResponse(1L, 1, BigDecimal.valueOf(1000));
    }
}
