package com.example.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TemporaryOrderEntityRepository implements TemporaryOrderRepository {

    private final TemporaryOrderJpaRepository repository;

    public TemporaryOrderEntityRepository(TemporaryOrderJpaRepository repository) {
        this.repository = repository;
    }
}
