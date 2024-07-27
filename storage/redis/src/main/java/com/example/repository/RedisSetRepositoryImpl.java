package com.example.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public String validQuantity(String key, String value, int quantity) {
         return redisTemplate.execute(
                script(),
                List.of(key),
                value,
                String.valueOf(quantity)
        );

    }

    private RedisScript<String> script() {
        String script = """
                if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                    return '2'
                end
                
                if tonumber(ARGV[2]) > redis.call('SCARD', KEYS[1]) then
                    redis.call('SADD', KEYS[1], ARGV[1])
                    return '1'
                end
                
                return '3'
                """;

        return RedisScript.of(script, String.class);
    }
}
