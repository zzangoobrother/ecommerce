package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Queue {

    private Long id;
    private Long memberId;
    private String token;
    private Status status;
    private LocalDateTime createdAt;

    @Builder
    public Queue(Long id, Long memberId, String token, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
    }

    public boolean checkedProcessing() {
        return this.status == Status.PROCESSING;
    }

    public boolean checkedWaitting() {
        return this.status == Status.WAITING;
    }

    public void updateProcessing() {
        this.status = Status.PROCESSING;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", token='" + token + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
