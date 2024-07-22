package com.example.repository;

import com.example.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetailEntity, Long> {

    Optional<OrderDetailEntity> findByOrdersId(Long orderId);
}
