package com.example.repository;

import com.example.model.Payment;

public interface PaymentRepository {

    Payment getBy(String orderCode);

    Payment save(Payment payment);
}
