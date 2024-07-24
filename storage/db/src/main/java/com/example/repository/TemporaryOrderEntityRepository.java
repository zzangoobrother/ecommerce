package com.example.repository;

import com.example.entity.TemporaryOrderEntity;
import com.example.model.TemporaryOrder;
import org.springframework.stereotype.Repository;

@Repository
public class TemporaryOrderEntityRepository implements TemporaryOrderRepository {

    private final TemporaryOrderJpaRepository repository;

    public TemporaryOrderEntityRepository(TemporaryOrderJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TemporaryOrder save(TemporaryOrder temporaryOrder) {
        return repository.save(TemporaryOrderEntity.toTemporaryOrderEntity(temporaryOrder)).toTemporaryOrder();
    }
}
