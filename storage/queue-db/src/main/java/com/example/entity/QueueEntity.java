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

    @Column(name = "member_id", unique = true, nullable = false)
    private Long memberId;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public QueueEntity(Long id, Long memberId, String token, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.token = token;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static QueueEntity toQueueEntity(Queue queue) {
        return QueueEntity.builder()
                .id(queue.getId())
                .memberId(queue.getMemberId())
                .token(queue.getToken())
                .status(queue.getStatus())
                .createdAt(queue.getCreatedAt())
                .build();
    }

    public Queue toQueue() {
        return Queue.builder()
                .id(getId())
                .memberId(getMemberId())
                .token(getToken())
                .status(getStatus())
                .createdAt(getCreatedAt())
                .build();
    }
}
