package com.example.client;

import com.example.client.dto.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "orderClient", url = "${order.api.url}")
public interface OrderClient {

    @GetMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<OrderResponse> getBy(@RequestParam String orderCode);
}
