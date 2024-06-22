package com.example.entity;

import com.example.model.OrderDetail;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders_detail")
public class OrderDetailEntity extends BaseTimeEntity {
    @Id
    @Column(name = "orders_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_id", nullable = false)
    private Long ordersId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "status", nullable = false)
    private OrderDetail.OrderStatus status;

    @Builder
    public OrderDetailEntity(Long id, Long ordersId, Long productId, int quantity, BigDecimal price, OrderDetail.OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ordersId = ordersId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static OrderDetailEntity toOrderDetailEntity(OrderDetail orderDetail) {
        return OrderDetailEntity.builder()
                .id(orderDetail.getId())
                .ordersId(orderDetail.getOrdersId())
                .productId(orderDetail.getProductId())
                .quantity(orderDetail.getQuantity())
                .price(orderDetail.getPrice())
                .status(orderDetail.getStatus())
                .createdAt(orderDetail.getCreatedAt())
                .updatedAt(orderDetail.getUpdatedAt())
                .build();
    }

    public OrderDetail toOrderDetail() {
        return OrderDetail.builder()
                .id(getId())
                .ordersId(getOrdersId())
                .productId(getProductId())
                .quantity(getQuantity())
                .price(getPrice())
                .status(getStatus())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
