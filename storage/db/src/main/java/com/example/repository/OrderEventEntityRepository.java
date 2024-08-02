package com.example.repository;

import com.example.entity.OrderEventEntity;
import com.example.model.OrderEvent;
import com.example.model.OrderEventStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<OrderEvent> getAllBy(OrderEventStatus status) {
        return repository.findAllByStatusForUpdate(status).stream()
                .map(OrderEventEntity::toOrderEvent)
                .toList();
    }

    @Override
    public void saveAll(List<OrderEvent> orderEvents) {
        List<OrderEventEntity> orderEventEntities = orderEvents.stream()
                .map(OrderEventEntity::toOrderEventEntity)
                .toList();

        repository.saveAll(orderEventEntities);
    }
}
