package com.example.application.dto;

import com.example.model.Status;

public record QueueDto(
        Status status,
        int remainWaitCount
) {
}
