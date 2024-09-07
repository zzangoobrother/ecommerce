package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Queue {

    private Long id;
    private String token;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    @Builder
    public Queue(Long id, String token, Status status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.id = id;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
