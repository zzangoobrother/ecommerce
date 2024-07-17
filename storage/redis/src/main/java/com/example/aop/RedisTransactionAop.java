package com.example.aop;

import com.example.repository.RedisCountRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RedisTransactionAop {

    private final AopForTransaction aopForTransaction;
    private final RedisCountRepository redisCountRepository;

    public RedisTransactionAop(AopForTransaction aopForTransaction, RedisCountRepository redisCountRepository) {
        this.aopForTransaction = aopForTransaction;
        this.redisCountRepository = redisCountRepository;
    }

    @Around("@annotation(com.example.annotation.RedisTransaction)")
    public Object transaction(final ProceedingJoinPoint joinPoint) throws Throwable {
        redisCountRepository.multi();

        try {
            return aopForTransaction.proceed(joinPoint);
        } catch (InterruptedException e) {
            throw new InterruptedException();
        } finally {
            redisCountRepository.exec();
        }
    }
}
