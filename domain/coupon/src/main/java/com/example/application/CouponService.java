package com.example.application;

import com.example.model.Coupon;
import com.example.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public String create(int count) {
        Coupon coupon = Coupon.builder()
                .code(UUID.randomUUID().toString())
                .count(count)
                .createdAt(LocalDateTime.now())
                .build();

        return couponRepository.create(coupon).getCode();
    }
}
