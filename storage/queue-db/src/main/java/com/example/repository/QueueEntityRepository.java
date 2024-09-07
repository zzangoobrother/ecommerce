package com.example.repository;

import org.springframework.stereotype.Repository;

@Repository
public class QueueEntityRepository implements QueueRepository {

    private final QueueJpaRepository repository;

    public QueueEntityRepository(QueueJpaRepository repository) {
        this.repository = repository;
    }
}
