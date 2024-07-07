package com.example.entity;

import com.example.model.Payment;
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
@Table(name = "payment")
public class PaymentEntity extends BaseTimeEntity {

    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_code", unique = true, nullable = false, length = 50)
    private String ordersCode;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Builder
    public PaymentEntity(Long id, String ordersCode, BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.ordersCode = ordersCode;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PaymentEntity toPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .ordersCode(payment.getOrdersCode())
                .price(payment.getPrice())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    public Payment toPayment() {
        return Payment.builder()
                .id(getId())
                .ordersCode(getOrdersCode())
                .price(getPrice())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
