package com.example.controller;

import com.example.application.KafkaQueueService;
import com.example.application.RedisQueueService;
import com.example.application.dto.KafkaQueueDto;
import com.example.controller.dto.QueueKafkaResponse;
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
    private final KafkaQueueService kafkaQueueService;

    public QueueController(RedisQueueService queueService, KafkaQueueService kafkaQueueService) {
        this.queueService = queueService;
        this.kafkaQueueService = kafkaQueueService;
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

    @GetMapping("/kafka")
    public QueueKafkaResponse send() {
        KafkaQueueDto dto = kafkaQueueService.send();
        return QueueKafkaResponse.to(dto);
    }

    @GetMapping("/kafka/{remainWaitCount}")
    public QueueKafkaResponse getKafkaBy(@PathVariable long remainWaitCount, HttpServletRequest request) {
        kafkaQueueService.getBy(request.getHeader("queue-token"), remainWaitCount);
        return QueueKafkaResponse.to();
    }
}
