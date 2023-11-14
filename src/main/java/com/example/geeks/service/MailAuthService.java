package com.example.geeks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailAuthService {
    private final RedisTemplate<String, String> redisTemplate;

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void saveDataWithExpiration(String key, String value, long timeoutInSeconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeoutInSeconds, TimeUnit.SECONDS);
    }
}
