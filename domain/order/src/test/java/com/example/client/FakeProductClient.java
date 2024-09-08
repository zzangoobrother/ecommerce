package com.example.client;

import com.example.client.dto.request.ProductDecreaseRequest;
import com.example.client.dto.response.ProductResponse;

import java.math.BigDecimal;

public class FakeProductClient implements ProductClient {

    private int quantity = 3;

    @Override
    public ProductResponse getBy(Long productId, String token) {
        return new ProductResponse(1L, "후드티", BigDecimal.valueOf(1000), quantity);
    }

    @Override
    public ProductResponse decrease(Long productId, ProductDecreaseRequest request) {
        return new ProductResponse(1L, "후드티", BigDecimal.valueOf(1000), quantity - request.quantity());
    }
}
