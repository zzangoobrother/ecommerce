package com.example.repository;

import com.example.entity.CouponEntity;
import com.example.model.Coupon;
import org.springframework.stereotype.Repository;

@Repository
class CouponEntityRepository implements CouponRepository {

    private final CouponJpaRepository repository;

    public CouponEntityRepository(CouponJpaRepository repository) {
        this.repository = repository;
    }


    @Override
    public Coupon create(Coupon coupon) {
        return repository.save(CouponEntity.toCouponEntity(coupon)).toCoupon();
    }
}
