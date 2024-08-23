package com.example;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqClient<T> {

    private final RabbitTemplate rabbitTemplate;

    public RabbitmqClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String exchangeName, String routingKey, T data) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, data);
    }
}
