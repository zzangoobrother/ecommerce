package com.example.controller;

import com.example.application.DbQueueService;
import com.example.controller.dto.QueueRequest;
import com.example.controller.dto.QueueResponse;
import com.example.global.config.auth.AuthMember;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/queues")
@RestController
public class QueueController {

    private final DbQueueService queueService;

    public QueueController(DbQueueService queueService) {
        this.queueService = queueService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String create(@AuthMember Long memberId) {
        return queueService.create(memberId);
    }

    @GetMapping
    public QueueResponse getBy(@AuthMember Long memberId, HttpServletRequest request) {
        return QueueResponse.to(queueService.getBy(memberId, request.getHeader("queue-token")));
    }

    @PostMapping("/complete")
    public void complete(@RequestBody QueueRequest request) {
        queueService.complete(request.token());
    }
}
