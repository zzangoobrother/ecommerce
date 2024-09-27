package com.example.application.listener;

import com.example.RabbitmqClient;
import com.example.application.listener.dto.OrderEventDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PaymentEventListener {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.product.decrease.key}")
    private String routingProductDecreaseKey;

    private final RabbitmqClient rabbitmqClient;

    public PaymentEventListener(RabbitmqClient rabbitmqClient) {
        this.rabbitmqClient = rabbitmqClient;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void orderMessage(String code) {
        rabbitmqClient.send(exchangeName, routingProductDecreaseKey, new OrderEventDto(code, 0));
    }
}
