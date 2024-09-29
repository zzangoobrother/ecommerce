package com.example.repository;

import com.example.model.OrderDetail;

import java.util.*;

public class InMemoryOrderDetailRepository implements OrderDetailRepository {

    private final Map<Long, OrderDetail> orderDetails = new HashMap<>();

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        if (Objects.isNull(orderDetail.getId())) {
            int size = orderDetails.size();
            orderDetail = OrderDetail.builder()
                    .id(size == 0 ? 1 : Collections.max(orderDetails.keySet()) + 1)
                    .ordersId(orderDetail.getOrdersId())
                    .productId(orderDetail.getProductId())
                    .quantity(orderDetail.getQuantity())
                    .price(orderDetail.getPrice())
                    .status(orderDetail.getStatus())
                    .createdAt(orderDetail.getCreatedAt())
                    .updatedAt(orderDetail.getUpdatedAt())
                    .build();
        }

        orderDetails.put(orderDetail.getId(), orderDetail);
        return orderDetail;
    }

    @Override
    public List<OrderDetail> getAllBy(Long orderId) {
        return orderDetails.values().stream()
                .filter(it -> Objects.equals(it.getOrdersId(), orderId))
                .toList();
    }

    @Override
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(this::save)
                .toList();
    }
}
