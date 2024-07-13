package com.example.controller;

import com.example.application.PaymentService;
import com.example.controller.dto.request.PaymentCancelRequest;
import com.example.controller.dto.request.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/payments")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public void payment(@RequestBody PaymentRequest request) {
        paymentService.payment(request.orderCode(), request.price());
    }

    @PostMapping("/cancel")
    public void cancel(@RequestBody PaymentCancelRequest request) {
        paymentService.cancel(request.orderCode());
    }
}
