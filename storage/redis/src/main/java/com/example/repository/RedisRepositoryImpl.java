package com.example.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisRepositoryImpl implements RedisRepository {

    private final StringRedisTemplate redisTemplate;

    public RedisRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void add(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }


}
