package com.example.repository;

import com.example.entity.OrderEventEntity;
import com.example.model.OrderEvent;
import org.springframework.stereotype.Repository;

@Repository
public class OrderEventEntityRepository implements OrderEventRepository {
    private final OrderEventJpaRepository repository;

    public OrderEventEntityRepository(OrderEventJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderEvent save(OrderEvent orderEvent) {
        return repository.save(OrderEventEntity.toOrderEventEntity(orderEvent)).toOrderEvent();
    }
}
