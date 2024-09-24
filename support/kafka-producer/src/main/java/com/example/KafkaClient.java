package com.example;

import com.example.config.SendDto;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaClient<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public KafkaClient(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public SendDto send(String topic, T data) {
        try {
            RecordMetadata recordMetadata = kafkaTemplate.send(topic, data).get().getRecordMetadata();
            long offset = recordMetadata.offset();
            int partition = recordMetadata.partition();
            return new SendDto(offset, partition);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
