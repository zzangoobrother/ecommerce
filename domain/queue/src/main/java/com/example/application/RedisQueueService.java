package com.example.application;

import com.example.application.dto.QueueDto;
import com.example.model.Status;
import com.example.repository.RedisZSetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@Service
public class RedisQueueService {

    private final String WAITING_QUEUE_KEY = "waiting_queue";
    private final String PROCESSING_QUEUE_KEY = "processing_queue";
    private final String COMPLETE_QUEUE_KEY = "complete_queue";

    private final int ALLOWED_QUEUE_SIZE = 1;

    private final RedisZSetRepository repository;

    public RedisQueueService(RedisZSetRepository repository) {
        this.repository = repository;
    }

    public void checkedByProcessing(String token) {
        if (repository.isProcessingQueue(PROCESSING_QUEUE_KEY, token)) {
            throw new IllegalStateException("해당 토큰을 확인해주세요.");
        }
    }

    public String create(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        String token = TokenSequence.create(now);
        repository.add(WAITING_QUEUE_KEY, token, now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        return token;
    }

    public QueueDto getBy(Long memberId, String token) {
        boolean result = repository.isWaitingQueue(WAITING_QUEUE_KEY, token);

        int remainWaitCount = 0;
        if (result) {
            remainWaitCount = repository.countRemainWait(WAITING_QUEUE_KEY, token);
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
