package com.example.repository;

import com.example.model.OrderDetail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public OrderDetail getBy(Long orderId) {
        return orderDetails.values().stream()
                .filter(it -> it.getOrdersId().equals(orderId))
                .findFirst().get();
    }
}
