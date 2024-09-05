package com.example.client;

import com.example.client.dto.request.PaymentCancelRequest;
import com.example.client.dto.request.PaymentRequest;
import com.example.client.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "paymentClient", url = "${payment.api.url}")
public interface PaymentClient {

    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    void payment(@RequestBody PaymentRequest request);

    @PostMapping(value = "/payments/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    void cancel(@RequestBody PaymentCancelRequest request);
}
