package com.example.listener;

import com.example.RabbitmqClient;
import com.example.application.PaymentService;
import com.example.application.dto.PaymentCancelDto;
import com.example.listener.dto.OrderEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentListener {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.payment.key}")
    private String routingPaymentKey;

    private final PaymentService paymentService;
    private final RabbitmqClient rabbitmqClient;

    public PaymentListener(PaymentService paymentService, RabbitmqClient rabbitmqClient) {
        this.paymentService = paymentService;
        this.rabbitmqClient = rabbitmqClient;
    }

    @RabbitListener(queues = "${rabbitmq.queue.payment.name}")
    public void paymentListener(OrderEventDto orderEventDto) {
        try {
            if (orderEventDto.getCount() > 3) {
                log.info("결제 3회 실패 Dead Letter Queue 이동");
                return;
            }

            paymentService.payment(orderEventDto.getOrderCode());
        } catch (RuntimeException e) {
            orderEventDto.increasedCount();
            log.info("결제 실패 count : {}", orderEventDto.getCount());

            rabbitmqClient.send(exchangeName, routingPaymentKey, orderEventDto);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.payment.cancel.name}")
    public void paymentCancelListener(PaymentCancelDto paymentCancelDto) {
        paymentService.cancel(paymentCancelDto.orderCode());
    }
}
