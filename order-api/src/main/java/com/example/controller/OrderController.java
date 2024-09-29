package com.example.controller;

import com.example.application.OrderService;
import com.example.application.dto.OrderDto;
import com.example.application.dto.OrderRequestDto;
import com.example.controller.dto.request.OrderRequest;
import com.example.controller.dto.response.OrderResponse;
import com.example.global.config.auth.AuthMember;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String order(@RequestBody OrderRequest orderRequest, @AuthMember Long memberId, HttpServletRequest request) {
//        return orderService.order(request.productId(), request.quantity(), memberId);

//        return orderService.orderByRedisLock(request.productId(), request.quantity(), memberId);
        String token = request.getHeader("queue-token");
        List<OrderRequestDto> dtos = orderRequest.requests().stream()
                .map(it -> new OrderRequestDto(it.productId(), it.quantity()))
                .toList();
        return orderService.orderByRabbitmq(dtos, memberId, token);
    }

    @GetMapping
    public List<OrderResponse> getBy(@RequestParam String orderCode) {
        List<OrderDto> orderDto = orderService.getBy(orderCode);
        return orderDto.stream()
                .map(it -> new OrderResponse(it.productId(), it.quantity(), it.price()))
                .toList();
    }
}
