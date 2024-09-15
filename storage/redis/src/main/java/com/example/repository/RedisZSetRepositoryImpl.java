package com.example.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Set;

@Repository
public class RedisZSetRepositoryImpl implements RedisZSetRepository {

    private final StringRedisTemplate redisTemplate;

    public RedisZSetRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void add(String key, String token, long expirationTime) {
        redisTemplate.opsForZSet().add(key, token, expirationTime);
    }

    @Override
    public boolean isWaitingQueue(String key, String token) {
        Double score = redisTemplate.opsForZSet().score(key, token);
        return !Objects.isNull(score);
    }

    @Override
    public boolean isProcessingQueue(String key, String token) {
        Double score = redisTemplate.opsForZSet().score(key, token);
        return !Objects.isNull(score);
    }

    @Override
    public int countRemainWait(String key, String token) {
        Long rank = redisTemplate.opsForZSet().rank(key, token);
        return Objects.isNull(rank) ? 0 : Integer.parseInt(String.valueOf(rank));
    }

    @Override
    public int countBy(String key) {
        Long size = redisTemplate.opsForZSet().size(key);
        return Objects.isNull(size) ? 0 : Integer.parseInt(String.valueOf(size));
    }

    @Override
    public Set<String> getWaitingToProcessing(String key, int neededCount) {
        return redisTemplate.opsForZSet()
                .range(key, 0, neededCount - 1);
    }

    @Override
    public void removeBy(String key, String token) {
        redisTemplate.opsForZSet().remove(key, token);
    }

    @Override
    public Set<String> getAllBy(String key) {
        return redisTemplate.opsForZSet()
                .range(key, 0, -1);
    }
}
