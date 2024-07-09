package com.example.client;

import com.example.client.dto.request.ProductDecreaseRequest;
import com.example.client.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "productClient", url = "${product.api.url}")
public interface ProductClient {

    @GetMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ProductResponse getBy(@PathVariable Long productId);

    @PostMapping(value = "/{productId}/decrease", consumes = MediaType.APPLICATION_JSON_VALUE)
    ProductResponse decrease(@PathVariable Long productId, @RequestBody ProductDecreaseRequest request);
}
