package com.example;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class KafkaConsumerClient {

    private final KafkaConsumer<String, String> kafkaConsumer;

    public KafkaConsumerClient(KafkaConsumer<String, String> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public long getPosition(String topic) {
        TopicPartition topicPartition = new TopicPartition(topic, 1);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        return kafkaConsumer.position(topicPartition) - 1;
    }

    public long getEndPosition(String topic) {
        TopicPartition topicPartition = new TopicPartition(topic, 1);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        kafkaConsumer.seekToEnd(Collections.singletonList(topicPartition));
        return kafkaConsumer.position(topicPartition) - 1;
    }
}
