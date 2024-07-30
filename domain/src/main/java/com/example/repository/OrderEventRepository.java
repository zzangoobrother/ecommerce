package com.example.repository;

import com.example.model.OrderEvent;

public interface OrderEventRepository {
    OrderEvent save(OrderEvent build);

}
