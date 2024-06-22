package com.example.repository;

public interface RedisRepository {

    boolean hasKey(String key);

    void add(String key, String value);

    Object get(String key);
}
