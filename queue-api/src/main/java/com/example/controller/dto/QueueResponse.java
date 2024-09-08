package com.example.controller.dto;

import com.example.application.dto.QueueDto;
import com.example.model.Status;

import java.time.LocalDateTime;

public record QueueResponse(
        Long queueId,
        Status status,
        LocalDateTime createdAt,
        int remainWaitCount
) {
    public static QueueResponse to(QueueDto queueDto) {
        return new QueueResponse(queueDto.queueId(), queueDto.status(), queueDto.createdAt(), queueDto.remainWaitCount());
    }
}
