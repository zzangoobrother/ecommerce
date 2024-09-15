package com.example.schedule;

import com.example.application.RedisQueueService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueEventSchedule {

    private final RedisQueueService dbQueueService;

    public QueueEventSchedule(RedisQueueService dbQueueService) {
        this.dbQueueService = dbQueueService;
    }

    @Scheduled(fixedRate = 5000)
    public void run() {
        dbQueueService.maintainProcessing();
        dbQueueService.deletedCompleted();
    }
}
