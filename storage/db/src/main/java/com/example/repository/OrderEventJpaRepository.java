package com.example.repository;

import com.example.entity.OrderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventJpaRepository extends JpaRepository<OrderEventEntity, Long> {
}
