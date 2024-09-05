package com.example.repository;

import com.example.model.Order;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();

    @Override
    public Order save(Order order) {
        if (Objects.isNull(order.getId())) {
            int size = orders.size();
            order = Order.builder()
                    .id(size == 0 ? 1 : Collections.max(orders.keySet()) + 1)
                    .memberId(order.getMemberId())
                    .ordersCode(order.getOrdersCode())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
        }

        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Order getBy(String orderCode) {
        return orders.values().stream()
                .filter(it -> it.getOrdersCode().equals(orderCode))
                .findFirst().get();
    }
}
