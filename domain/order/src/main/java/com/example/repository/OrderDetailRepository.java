package com.example.repository;

import com.example.model.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {
    OrderDetail save(OrderDetail orderDetail);

    List<OrderDetail> getAllBy(Long orderId);

    List<OrderDetail> saveAll(List<OrderDetail> orderDetails);
}
