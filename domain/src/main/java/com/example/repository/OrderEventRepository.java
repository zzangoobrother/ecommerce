package com.example.repository;

import com.example.model.OrderEvent;
import com.example.model.OrderEventStatus;

import java.util.List;

public interface OrderEventRepository {
    OrderEvent save(OrderEvent build);

    List<OrderEvent> getAllBy(OrderEventStatus status);

    void saveAll(List<OrderEvent> orderEvents);
}
