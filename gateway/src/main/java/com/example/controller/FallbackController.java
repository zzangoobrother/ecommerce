package com.example.controller;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/fallback-circuit")
    public Mono<Void> fallBack(ServerWebExchange exchange) {
        Throwable throwable = exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR);
        if (throwable instanceof TimeoutException) {
            throw new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, "Service Timeout");
        } else if (throwable instanceof CallNotPermittedException) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service is not available");
        }

        return null;
    }
}
