package com.example.application.dto;

import com.example.model.Status;

public record KafkaQueueDto(
        String token,
        long remainWaitCount
) {
}
