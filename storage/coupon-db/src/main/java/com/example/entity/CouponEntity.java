package com.example.entity;

import com.example.model.Coupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon")
public class CouponEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "count", nullable = false)
    private int count;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public CouponEntity(Long id, String code, int count, LocalDateTime createdAt) {
        this.id = id;
        this.code = code;
        this.count = count;
        this.createdAt = createdAt;
    }

    public static CouponEntity toCouponEntity(Coupon coupon) {
        return CouponEntity.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .count(coupon.getCount())
                .createdAt(coupon.getCreatedAt())
                .build();
    }

    public Coupon toCoupon() {
        return Coupon.builder()
                .id(getId())
                .code(getCode())
                .count(getCount())
                .createdAt(getCreatedAt())
                .build();
    }
}
