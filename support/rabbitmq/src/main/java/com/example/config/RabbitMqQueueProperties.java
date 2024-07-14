package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMqQueueProperties(
        String exchangeName,
        String productDecreaseName,
        String paymentName
) {

}
