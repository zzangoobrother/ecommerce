package com.example.repository;

import com.example.model.Payment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<Long, Payment> payments = new HashMap<>();

    @Override
    public Payment getBy(String orderCode) {
        return payments.values().stream()
                .filter(it -> it.getOrdersCode().equals(orderCode))
                .findFirst().get();
    }

    @Override
    public Payment save(Payment payment) {
        if (Objects.isNull(payment.getId())) {
            int size = payments.size();
            payment = Payment.builder()
                    .id(size == 0 ? 1 : Collections.max(payments.keySet()) + 1)
                    .ordersCode(payment.getOrdersCode())
                    .price(payment.getPrice())
                    .status(payment.getStatus())
                    .createdAt(payment.getCreatedAt())
                    .updatedAt(payment.getUpdatedAt())
                    .build();
        }

        payments.put(payment.getId(), payment);
        return payment;
    }
}
