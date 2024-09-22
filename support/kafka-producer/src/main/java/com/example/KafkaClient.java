package com.example;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaClient<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public KafkaClient(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, T>> send(String topic, T data) {
        return kafkaTemplate.send(topic, data);
    }
}
