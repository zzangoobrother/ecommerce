package com.example.repository;

import com.example.entity.TemporaryOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemporaryOrderJpaRepository extends JpaRepository<TemporaryOrderEntity, Long> {
}
