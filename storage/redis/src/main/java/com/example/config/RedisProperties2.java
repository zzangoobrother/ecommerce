package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.data.redis2")
public record RedisProperties2(
        String host,
        String port
) {
}
