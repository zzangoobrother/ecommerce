package com.example.entity;

import com.example.model.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseTimeEntity {
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "orders_code", unique = true, nullable = false, length = 50)
    private String ordersCode;

    @Builder
    public OrderEntity(Long id, Long memberId, String ordersCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.ordersCode = ordersCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .memberId(order.getMemberId())
                .ordersCode(order.getOrdersCode())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public Order toOrder() {
        return Order.builder()
                .id(getId())
                .memberId(getMemberId())
                .ordersCode(getOrdersCode())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
