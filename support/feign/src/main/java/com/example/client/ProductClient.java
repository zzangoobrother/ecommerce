package com.example.client;

import com.example.client.dto.request.ProductDecreaseRequest;
import com.example.client.dto.response.ProductResponse;
import feign.HeaderMap;
import feign.Headers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "productClient", url = "${product.api.url}")
public interface ProductClient {

    @Cacheable(cacheNames = "productResponse", key = "#productId", cacheManager = "cacheManager")
    @GetMapping(value = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ProductResponse getBy(@PathVariable Long productId, @RequestHeader("queue-token") String token);

    @PostMapping(value = "/products/{productId}/decrease", consumes = MediaType.APPLICATION_JSON_VALUE)
    ProductResponse decrease(@PathVariable Long productId, @RequestBody ProductDecreaseRequest request);
}
