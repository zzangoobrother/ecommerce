package com.example.application;

import com.example.application.dto.QueueDto;
import com.example.repository.RedisZSetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedisQueueService {

    private final String WAITING_QUEUE_KEY = "waiting_queue";
    private final String PROCESSING_QUEUE_KEY = "processing_queue";

    private final int ALLOWED_QUEUE_SIZE = 1;

    private final RedisZSetRepository repository;

    public RedisQueueService(RedisZSetRepository repository) {
        this.repository = repository;
    }

    public void checkedByProcessing(String token) {

    }

    public String create(Long memberId) {

    }

    public QueueDto getBy(Long memberId, String token) {

    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void maintainProcessing() {

    }

    public void complete(String token) {

    }

    public void deletedCompleted() {

    }
}
