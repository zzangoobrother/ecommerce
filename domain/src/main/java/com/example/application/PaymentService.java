package com.example.application;

import com.example.model.Payment;
import com.example.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void payment(String orderCode, BigDecimal price) {
        paymentRepository.payment(Payment.builder()
                .ordersCode(orderCode)
                .price(price)
                .status(Payment.Status.SUCCESS)
                .build());
    }

    public void cancel(String orderCode) {
        Payment payment = paymentRepository.getBy(orderCode);
        if (payment.getStatus() == Payment.Status.CANCEL) {
            throw new IllegalStateException("주문 취소된 결제 건 입니다.");
        }

        payment.cancel();

        paymentRepository.save(payment);
    }
}
