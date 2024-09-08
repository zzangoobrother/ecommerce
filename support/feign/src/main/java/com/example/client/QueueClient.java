package com.example.client;

import com.example.client.dto.request.QueueRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "queueClient", url = "${queue.api.url}")
public interface QueueClient {

    @PostMapping(value = "/queue/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    void complete(@RequestBody QueueRequest request);
}
