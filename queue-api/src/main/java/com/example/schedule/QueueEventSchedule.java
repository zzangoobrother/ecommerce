package com.example.schedule;

import com.example.application.QueueService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueEventSchedule {

    private final QueueService queueService;

    public QueueEventSchedule(QueueService queueService) {
        this.queueService = queueService;
    }

    @Scheduled(fixedRate = 5000)
    public void run() {
        queueService.maintainProcessing();
        queueService.deletedCompleted();
    }
}
