package com.example.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisSetRepositoryImpl implements RedisSetRepository {

    private final StringRedisTemplate redisTemplate;

    public RedisSetRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void add(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public void remove(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }
}
