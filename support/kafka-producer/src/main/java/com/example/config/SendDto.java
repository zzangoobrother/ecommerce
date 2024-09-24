package com.example.config;

public record SendDto(
        long offset,
        int partition
) {
}
