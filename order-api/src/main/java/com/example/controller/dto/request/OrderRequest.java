package com.example.controller.dto.request;

import java.util.List;

public record OrderRequest(
        List<Request> requests
) {

    public record Request(
            Long productId,
            int quantity
    ) {}
}
