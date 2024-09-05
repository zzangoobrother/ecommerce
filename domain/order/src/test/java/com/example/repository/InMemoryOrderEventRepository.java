package com.example.repository;

import com.example.model.OrderEvent;
import com.example.model.OrderEventStatus;

import java.util.*;

public class InMemoryOrderEventRepository implements OrderEventRepository {

    private final Map<Long, OrderEvent> orderEvents = new HashMap<>();

    @Override
    public OrderEvent save(OrderEvent orderEvent) {
        if (Objects.isNull(orderEvent.getId())) {
            int size = orderEvents.size();
            orderEvent = OrderEvent.builder()
                    .id(size == 0 ? 1 : Collections.max(orderEvents.keySet()) + 1)
                    .ordersCode(orderEvent.getOrdersCode())
                    .status(orderEvent.getStatus())
                    .createdAt(orderEvent.getCreatedAt())
                    .updatedAt(orderEvent.getUpdatedAt())
                    .build();
        }

        orderEvents.put(orderEvent.getId(), orderEvent);
        return orderEvent;
    }

    @Override
    public List<OrderEvent> getAllBy(OrderEventStatus status) {
        return orderEvents.values().stream()
                .filter(it -> it.getStatus() == status)
                .toList();
    }

    @Override
    public void saveAll(List<OrderEvent> orderEvents) {
        for (OrderEvent orderEvent : orderEvents) {
            save(orderEvent);
        }
    }
}
