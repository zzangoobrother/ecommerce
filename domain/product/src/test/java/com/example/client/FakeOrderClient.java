package com.example.client;

import com.example.client.dto.response.OrderResponse;

import java.math.BigDecimal;
import java.util.List;

public class FakeOrderClient implements OrderClient {
    @Override
    public List<OrderResponse> getBy(String orderCode) {
        return List.of(new OrderResponse(1L, 1, BigDecimal.valueOf(1000)));
    }
}
