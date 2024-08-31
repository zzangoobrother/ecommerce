package com.example.repository;

import com.example.model.Order;

public interface OrderRepository {
    Order save(Order order);

    Order getBy(String orderCode);
}
