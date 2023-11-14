package com.example.geeks.requestDto;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "null", timeToLive = 180)
public class MailAuthDto {
    private String email;

    private String code;
}
