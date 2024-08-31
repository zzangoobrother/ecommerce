package com.example.repository;

import com.example.entity.OrderEntity;
import com.example.model.Order;
import org.springframework.stereotype.Repository;

@Repository
class OrderEntityRepository implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderEntityRepository(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.toOrderEntity(order)).toOrder();
    }

    @Override
    public Order getBy(String orderCode) {
        return orderJpaRepository.findByOrdersCode(orderCode).orElseThrow(
                () -> new IllegalArgumentException("해당 주문서를 찾을 수 없습니다.")
        ).toOrder();
    }
}
