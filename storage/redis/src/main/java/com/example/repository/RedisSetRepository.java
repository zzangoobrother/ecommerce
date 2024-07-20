package com.example.repository;

public interface RedisSetRepository {
    void add(String key, String value);

    Long size(String key);
}
