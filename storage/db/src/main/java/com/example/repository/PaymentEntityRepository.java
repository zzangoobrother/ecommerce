package com.example.repository;

import com.example.entity.PaymentEntity;
import com.example.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Slf4j
@Repository
class PaymentEntityRepository implements PaymentRepository {

    private final PaymentJpaRepository repository;

    public PaymentEntityRepository(PaymentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void payment(BigDecimal price) {
        log.info(price + "원 결제가 완료 되었습니다.");
    }

    @Override
    public Payment payment(Payment payment) {
        log.info(payment.getPrice() + "원 결제가 완료 되었습니다.");
        return repository.save(PaymentEntity.toPaymentEntity(payment)).toPayment();
    }
}
