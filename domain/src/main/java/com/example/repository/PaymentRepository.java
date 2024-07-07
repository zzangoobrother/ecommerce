package com.example.repository;

import com.example.model.Payment;

import java.math.BigDecimal;

public interface PaymentRepository {

    void payment(BigDecimal price);

    Payment payment(Payment payment);
}
