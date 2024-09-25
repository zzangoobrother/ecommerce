package com.example.controller;

import com.example.application.RedisQueueService;
import com.example.controller.dto.QueueRequest;
import com.example.controller.dto.QueueResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/queues")
@RestController
public class QueueController {

    private final RedisQueueService queueService;

    public QueueController(RedisQueueService queueService) {
        this.queueService = queueService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String create() {
        return queueService.create();
    }

    @GetMapping
    public QueueResponse getBy(HttpServletRequest request) {
        return QueueResponse.to(queueService.getBy(request.getHeader("queue-token")));
    }

    @PostMapping("/complete")
    public void complete(@RequestBody QueueRequest request) {
        queueService.complete(request.token());
    }
}
