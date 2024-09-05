package com.example.client;

import com.example.repository.RedisSetRepository;

public class FakeRedisSetRepository implements RedisSetRepository {
    @Override
    public void add(String key, String value) {

    }

    @Override
    public Long size(String key) {
        return null;
    }

    @Override
    public void remove(String key, String value) {

    }

    @Override
    public String validQuantity(String key, String value, int quantity) {
        return null;
    }
}
