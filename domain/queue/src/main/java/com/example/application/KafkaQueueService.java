package com.example.application;

import com.example.KafkaClient;
import com.example.application.dto.QueueDto;
import com.example.model.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Service
public class KafkaQueueService {

    private final KafkaClient<String> kafkaClient;

    public KafkaQueueService(KafkaClient<String> kafkaClient) {
        this.kafkaClient = kafkaClient;
    }

    public void checkedByProcessing(String token) {
        if (repository.isProcessingQueue(PROCESSING_QUEUE_KEY, token)) {
            throw new IllegalStateException("해당 토큰을 확인해주세요.");
        }
    }

    public String create() {
        LocalDateTime now = LocalDateTime.now();
        String token = TokenSequence.create(now);
        repository.add(WAITING_QUEUE_KEY, token, now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        return token;
    }

    public QueueDto getBy(String token) {
        boolean result = repository.isWaitingQueue(WAITING_QUEUE_KEY, token);

        int remainWaitCount = 0;
        if (result) {
            remainWaitCount = repository.countRemainWait(WAITING_QUEUE_KEY, token) + 1;
        }

        return new QueueDto(
                remainWaitCount == 0 ? Status.PROCESSING : Status.WAITING,
                remainWaitCount
        );
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void maintainProcessing() {
        int count = repository.countBy(PROCESSING_QUEUE_KEY);
        int neededCount = ALLOWED_QUEUE_SIZE - count;

        if (neededCount == 0) {
            return;
        }

        Set<String> tokens = repository.getWaitingToProcessing(WAITING_QUEUE_KEY, neededCount);
        for (String token : tokens) {
            repository.removeBy(WAITING_QUEUE_KEY, token);

            LocalDateTime now = LocalDateTime.now();
            repository.add(PROCESSING_QUEUE_KEY, token, now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }
    }

    public void complete(String token) {
        LocalDateTime now = LocalDateTime.now();
        repository.add(COMPLETE_QUEUE_KEY, token, now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    public void deletedCompleted() {
        Set<String> tokens = repository.getAllBy(COMPLETE_QUEUE_KEY);
        tokens.forEach(token -> repository.removeBy(COMPLETE_QUEUE_KEY, token));
    }
}
