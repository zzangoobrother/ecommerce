package com.example.repository;

import com.example.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetailEntity, Long> {
}
