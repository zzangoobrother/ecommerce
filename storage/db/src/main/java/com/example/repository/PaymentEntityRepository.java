package com.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Slf4j
@Repository
class PaymentEntityRepository implements PaymentRepository {
    @Override
    public void payment(BigDecimal price) {
        log.info(price + "원 결제가 완료 되었습니다.");
    }
}
