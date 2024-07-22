package com.example.repository;

import com.example.entity.OrderDetailEntity;
import com.example.model.OrderDetail;
import org.springframework.stereotype.Repository;

@Repository
class OrderDetailEntityRepository implements OrderDetailRepository {

    private final OrderDetailJpaRepository orderDetailJpaRepository;

    public OrderDetailEntityRepository(OrderDetailJpaRepository orderDetailJpaRepository) {
        this.orderDetailJpaRepository = orderDetailJpaRepository;
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailJpaRepository.save(OrderDetailEntity.toOrderDetailEntity(orderDetail)).toOrderDetail();
    }

    @Override
    public OrderDetail getBy(Long orderId) {
        return orderDetailJpaRepository.findByOrdersId(orderId).orElseThrow(
                () -> new IllegalArgumentException("해당 주문서를 찾을 수 없습니다.")
        ).toOrderDetail();
    }
}
