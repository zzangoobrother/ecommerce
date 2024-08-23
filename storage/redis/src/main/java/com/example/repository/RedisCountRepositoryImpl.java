package com.example.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCountRepositoryImpl implements RedisCountRepository {

    private final RedisTemplate<String, Long> redisTemplate;

    public RedisCountRepositoryImpl(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void add(String key, Long value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Long decrement(String key, Long value) {
        return redisTemplate.opsForValue().decrement(key, value);
    }

    @Override
    public Long increment(String key, Long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public Long get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
