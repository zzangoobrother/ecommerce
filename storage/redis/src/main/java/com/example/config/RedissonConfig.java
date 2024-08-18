package com.example.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    private static final String REDISSON_HOST_PREFIX = "redis://";

    private final RedisProperties2 redisProperties;

    public RedissonConfig(RedisProperties2 redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    public RedissonClient redissonClient() {
        RedissonClient redisson;
        Config config = new Config();
        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + redisProperties.host() + ":" + redisProperties.port());
        redisson = Redisson.create(config);
        return redisson;
    }
}
