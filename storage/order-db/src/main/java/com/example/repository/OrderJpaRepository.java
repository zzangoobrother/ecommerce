package com.example.repository;

import com.example.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrdersCode(String orderCode);
}
