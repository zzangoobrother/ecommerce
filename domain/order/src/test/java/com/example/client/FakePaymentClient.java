package com.example.client;

import com.example.client.dto.request.PaymentCancelRequest;
import com.example.client.dto.request.PaymentRequest;

public class FakePaymentClient implements PaymentClient {
    @Override
    public void payment(PaymentRequest request) {

    }

    @Override
    public void cancel(PaymentCancelRequest request) {

    }
}
