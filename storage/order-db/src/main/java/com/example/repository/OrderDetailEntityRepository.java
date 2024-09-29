package com.example.repository;

import com.example.entity.OrderDetailEntity;
import com.example.model.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<OrderDetail> getAllBy(Long orderId) {
        return orderDetailJpaRepository.findAllByOrdersId(orderId).stream()
                .map(OrderDetailEntity::toOrderDetail)
                .toList();
    }

    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails) {
        List<OrderDetailEntity> orderDetailEntities = orderDetails.stream()
                .map(OrderDetailEntity::toOrderDetailEntity)
                .toList();
        return orderDetailJpaRepository.saveAll(orderDetailEntities).stream()
                .map(OrderDetailEntity::toOrderDetail)
                .toList();
    }
}
