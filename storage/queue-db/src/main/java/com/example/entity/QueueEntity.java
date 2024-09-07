package com.example.entity;

import com.example.model.Queue;
import com.example.model.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "queue")
public class QueueEntity {

    @Id
    @Column(name = "queue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Builder
    public QueueEntity(Long id, String token, Status status, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.id = id;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public static QueueEntity toQueueEntity(Queue queue) {
        return QueueEntity.builder()
                .id(queue.getId())
                .token(queue.getToken())
                .status(queue.getStatus())
                .createdAt(queue.getCreatedAt())
                .expiredAt(queue.getExpiredAt())
                .build();
    }

    public Queue toQueue() {
        return Queue.builder()
                .id(getId())
                .token(getToken())
                .status(getStatus())
                .createdAt(getCreatedAt())
                .expiredAt(getExpiredAt())
                .build();
    }
}
