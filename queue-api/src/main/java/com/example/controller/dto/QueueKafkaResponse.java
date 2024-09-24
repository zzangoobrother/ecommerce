package com.example.controller.dto;

import com.example.application.dto.KafkaQueueDto;

public record QueueKafkaResponse(
        String token,
        long remainWaitCount,
        int partition
) {
    public static QueueKafkaResponse to(KafkaQueueDto queueDto) {
        return new QueueKafkaResponse(queueDto.token(), queueDto.remainWaitCount(), queueDto.partition());
    }
}
