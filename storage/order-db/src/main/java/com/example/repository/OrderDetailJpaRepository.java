package com.example.repository;

import com.example.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetailEntity, Long> {

    Optional<OrderDetailEntity> findByOrdersId(Long orderId);

    List<OrderDetailEntity> findAllByOrdersId(Long orderId);
}
