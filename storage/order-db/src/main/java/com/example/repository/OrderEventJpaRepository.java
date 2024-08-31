package com.example.repository;

import com.example.entity.OrderEventEntity;
import com.example.model.OrderEventStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderEventJpaRepository extends JpaRepository<OrderEventEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from OrderEventEntity e where e.status = :status")
    List<OrderEventEntity> findAllByStatusForUpdate(@Param("status") OrderEventStatus status);
}
