package com.example.entity;

import com.example.model.TemporaryOrder;
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
@Table(name = "temporary_orders")
public class TemporaryOrderEntity extends BaseTimeEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "orders_code", unique = true, nullable = false, length = 50)
    private String ordersCode;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Builder
    public TemporaryOrderEntity(Long id, Long memberId, Long productId, String ordersCode, int quantity, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.ordersCode = ordersCode;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static TemporaryOrderEntity toTemporaryOrderEntity(TemporaryOrder temporaryOrder) {
        return TemporaryOrderEntity.builder()
                .id(temporaryOrder.getId())
                .memberId(temporaryOrder.getMemberId())
                .productId(temporaryOrder.getProductId())
                .ordersCode(temporaryOrder.getOrdersCode())
                .quantity(temporaryOrder.getQuantity())
                .price(temporaryOrder.getPrice())
                .build();
    }

    public TemporaryOrder toTemporaryOrder() {
        return TemporaryOrder.builder()
                .id(getId())
                .memberId(getMemberId())
                .productId(getProductId())
                .ordersCode(getOrdersCode())
                .quantity(getQuantity())
                .price(getPrice())
                .build();
    }
}
