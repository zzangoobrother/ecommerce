package com.example.repository;

import java.math.BigDecimal;

public interface PaymentRepository {

    void payment(BigDecimal price);
}
