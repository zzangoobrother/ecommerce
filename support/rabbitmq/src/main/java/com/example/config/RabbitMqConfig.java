package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private final RabbitMqProperties rabbitMqProperties;
    private final RabbitMqQueueProperties rabbitMqQueueProperties;

    public RabbitMqConfig(RabbitMqProperties rabbitMqProperties, RabbitMqQueueProperties rabbitMqQueueProperties) {
        this.rabbitMqProperties = rabbitMqProperties;
        this.rabbitMqQueueProperties = rabbitMqQueueProperties;
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(rabbitMqQueueProperties.exchangeName());
    }

    @Bean
    public Queue productDecreasQueue() {
        return new Queue(rabbitMqQueueProperties.productDecreaseName());
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(rabbitMqQueueProperties.paymentName());
    }

    @Bean
    public Binding productDecreasBinding(FanoutExchange exchange, Queue productDecreasQueue) {
        return BindingBuilder
                .bind(productDecreasQueue)
                .to(exchange);
    }

    @Bean
    public Binding paymentBinding(FanoutExchange exchange, Queue paymentQueue) {
        return BindingBuilder
                .bind(paymentQueue)
                .to(exchange);
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
