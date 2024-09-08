package com.example.repository;

import com.example.model.Queue;
import com.example.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueRepository {
    Queue getBy(Long memberId, String token);

    Queue getBy(String token);

    Queue save(Queue queue);

    int countRemainWait(Status status, LocalDateTime createdAt);

    int countBy(Status status);

    List<Queue> getAllBy(Status status, int neededCount);

    void saveAll(List<Queue> queues);
}
