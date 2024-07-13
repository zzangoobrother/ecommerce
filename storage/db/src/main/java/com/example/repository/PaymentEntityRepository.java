package com.example.repository;

import com.example.entity.PaymentEntity;
import com.example.model.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Repository
class PaymentEntityRepository implements PaymentRepository {

    private final PaymentJpaRepository repository;

    public PaymentEntityRepository(PaymentJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public Payment payment(Payment payment) {
        log.info(payment.getPrice() + "원 결제가 완료 되었습니다.");
        return repository.save(PaymentEntity.toPaymentEntity(payment)).toPayment();
    }

    @Override
    public Payment getBy(String orderCode) {
        return repository.findByOrdersCode(orderCode).orElseThrow(
                () -> new IllegalArgumentException("해당 결제가 존재하지 않습니다.")
        ).toPayment();
    }

    @Transactional
    @Override
    public Payment save(Payment payment) {
        return repository.save(PaymentEntity.toPaymentEntity(payment)).toPayment();
    }
}
