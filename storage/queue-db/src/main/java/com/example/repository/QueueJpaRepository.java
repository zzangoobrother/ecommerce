package com.example.repository;

import com.example.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueJpaRepository extends JpaRepository<Queue, Long> {
}
