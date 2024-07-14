package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.product.decrease.name}")
    private String queueProductDecreaseName;

    @Value("${rabbitmq.queue.payment.name}")
    private String queuePaymentName;

    @Value("${rabbitmq.queue.payment.cancel.name}")
    private String queuePaymentCancelName;

    @Value("${rabbitmq.routing.product.decrease.key}")
    private String routingProductDecreaseKey;

    @Value("${rabbitmq.routing.payment.key}")
    private String routingPaymentKey;

    @Value("${rabbitmq.routing.payment.cancel.key}")
    private String routingPaymentCancelKey;

    private final RabbitMqProperties rabbitMqProperties;
    private final RabbitMqQueueProperties rabbitMqQueueProperties;

    public RabbitMqConfig(RabbitMqProperties rabbitMqProperties, RabbitMqQueueProperties rabbitMqQueueProperties) {
        this.rabbitMqProperties = rabbitMqProperties;
        this.rabbitMqQueueProperties = rabbitMqQueueProperties;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Queue productDecreaseQueue() {
        return new Queue(queueProductDecreaseName);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(queuePaymentName);
    }

    @Bean
    public Queue paymentCancelQueue() {
        return new Queue(queuePaymentCancelName);
    }

    @Bean
    public Binding productDecreasBinding(DirectExchange exchange, Queue productDecreaseQueue) {
        return BindingBuilder
                .bind(productDecreaseQueue)
                .to(exchange)
                .with(routingProductDecreaseKey);
    }

    @Bean
    public Binding paymentBinding(DirectExchange exchange, Queue paymentQueue) {
        return BindingBuilder
                .bind(paymentQueue)
                .to(exchange)
                .with(routingPaymentKey);
    }

    @Bean
    public Binding paymentCancelBinding(DirectExchange exchange, Queue paymentCancelQueue) {
        return BindingBuilder
                .bind(paymentCancelQueue)
                .to(exchange)
                .with(routingPaymentCancelKey);
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMqProperties.host());
        connectionFactory.setPort(rabbitMqProperties.port());
        connectionFactory.setUsername(rabbitMqProperties.username());
        connectionFactory.setPassword(rabbitMqProperties.password());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
