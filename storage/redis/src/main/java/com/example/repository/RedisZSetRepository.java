package com.example.repository;

import java.util.Set;

public interface RedisZSetRepository {

    void add(String key, String token, long expirationTime);

    boolean isWaitingQueue(String key, String token);

    boolean isProcessingQueue(String key, String token);

    int countRemainWait(String key, String token);

    int countBy(String key);

    Set<String> getWaitingToProcessing(String key, int neededCount);

    void removeBy(String key, String token);

    Set<String> getAllBy(String key);
}
