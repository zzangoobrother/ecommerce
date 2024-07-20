package com.example.repository;

public interface RedisCountRepository {

    void add(String key, Long value);

    Long decrement(String key, Long value);

    Long increment(String key, Long value);

    Long get(String key);
}
