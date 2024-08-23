package com.example.repository;

public interface RedisSetRepository {
    void add(String key, String value);

    Long size(String key);

    void remove(String key, String value);

    String validQuantity(String key, String value, int quantity);
}
