package com.example.application.dto;

public record KafkaQueueDto(
        String token,
        long remainWaitCount,
        int partition
) {
}
