package com.example.controller;

import com.example.application.OrderService;
import com.example.controller.dto.request.OrderRequest;
import com.example.global.config.auth.AuthMember;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String order(@RequestBody OrderRequest request, @AuthMember Long memberId) {
        return orderService.order(request.productId(), request.quantity(), memberId);

//        return orderService.orderByRedisLock(request.productId(), request.quantity(), memberId);
    }
}
