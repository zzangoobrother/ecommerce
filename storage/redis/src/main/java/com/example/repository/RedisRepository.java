package com.example.repository;

public interface RedisRepository {

    boolean hasKey(String key);

    void add(String key, String value);

    String get(String key);
}
