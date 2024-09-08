package com.example.application.dto;

import com.example.model.Status;

import java.time.LocalDateTime;

public record QueueDto(
        Long queueId,
        Status status,
        LocalDateTime createdAt,
        int remainWaitCount
) {
}
