package com.example.application;

import com.example.repository.QueueRepository;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }
}
