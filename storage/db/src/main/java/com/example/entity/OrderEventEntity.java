package com.example.entity;

import com.example.model.OrderEvent;
import com.example.model.OrderEventStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders_event")
public class OrderEventEntity extends BaseTimeEntity {

    @Id
    @Column(name = "orders_event_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_code", unique = true, nullable = false, length = 50)
    private String ordersCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderEventStatus status;

    @Builder
    public OrderEventEntity(Long id, String ordersCode, OrderEventStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ordersCode = ordersCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderEventEntity toOrderEventEntity(OrderEvent orderEvent) {
        return OrderEventEntity.builder()
                .id(orderEvent.getId())
                .ordersCode(orderEvent.getOrdersCode())
                .status(orderEvent.getStatus())
                .createdAt(orderEvent.getCreatedAt())
                .updatedAt(orderEvent.getUpdatedAt())
                .build();
    }

    public OrderEvent toOrderEvent() {
        return OrderEvent.builder()
                .id(getId())
                .ordersCode(getOrdersCode())
                .status(getStatus())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
