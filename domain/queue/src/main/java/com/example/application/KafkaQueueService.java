package com.example.application;

import com.example.KafkaClient;
import com.example.KafkaConsumerClient;
import com.example.application.dto.KafkaQueueDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaQueueService {

    private static final String TOPIC_NAME = "ecommerce-queue";

    private final KafkaClient<String> kafkaClient;
    private final KafkaConsumerClient kafkaConsumerClient;

    public KafkaQueueService(KafkaClient<String> kafkaClient, KafkaConsumerClient kafkaConsumerClient) {
        this.kafkaClient = kafkaClient;
        this.kafkaConsumerClient = kafkaConsumerClient;
    }

    public KafkaQueueDto send() {
        LocalDateTime now = LocalDateTime.now();
        String token = TokenSequence.create(now);
        long remainWaitCount = kafkaClient.send(TOPIC_NAME, token);

        return new KafkaQueueDto(token, remainWaitCount);
    }

    public void getBy(String token, long remainWaitCount) {

    }


}
