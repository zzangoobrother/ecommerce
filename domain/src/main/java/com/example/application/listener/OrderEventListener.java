package com.example.application.listener;

import com.example.RabbitmqClient;
import com.example.application.event.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderEventListener {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.payment.key}")
    private String routingPaymentKey;

    @Value("${rabbitmq.routing.product.decrease.key}")
    private String routingProductDecreaseKey;

    private final RabbitmqClient rabbitmqClient;

    public OrderEventListener(RabbitmqClient rabbitmqClient) {
        this.rabbitmqClient = rabbitmqClient;
    }

    @Async
    @TransactionalEventListener
    public void orderMessage(OrderEvent orderEvent) {
        System.out.println("event listener");
        rabbitmqClient.send(exchangeName, routingPaymentKey, orderEvent.getPaymentDto());
        rabbitmqClient.send(exchangeName, routingProductDecreaseKey, orderEvent.getProductDecreaseDto());
    }
}
