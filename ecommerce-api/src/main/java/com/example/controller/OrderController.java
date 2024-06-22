package com.example.controller;

import com.example.application.OrderService;
import com.example.global.config.auth.AuthMember;
import com.example.controller.dto.request.OrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void order(@RequestBody OrderRequest request, @AuthMember Long memberId) {
        orderService.order(request.productId(), request.quantity(), memberId, LocalDateTime.now());
    }
}
