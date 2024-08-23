package com.example.listener;

import com.example.application.PaymentService;
import com.example.application.dto.PaymentCancelDto;
import com.example.application.dto.PaymentDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.payment.name}")
    public void paymentListener(String orderCode) {
        paymentService.payment(orderCode);
    }

    @RabbitListener(queues = "${rabbitmq.queue.payment.cancel.name}")
    public void paymentCancelListener(PaymentCancelDto paymentCancelDto) {
        paymentService.cancel(paymentCancelDto.orderCode());
    }
}
