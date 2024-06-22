package com.example.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
class PaymentEntityRepository implements PaymentRepository {
    @Override
    public void payment(BigDecimal price) {
        System.out.println(price + "원 결제가 완료 되었습니다.");
    }
}
