package com.test.task.redis_learn.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * author: user,
 * date: 04.06.2026
 */

@Service
@RequiredArgsConstructor
public class VerificationService {
//    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate redisTemplate;
    private final String keyPrefix = "auth:code:";

    public String generateCode(String email){
        String key = keyPrefix + email;

        Random random = new Random();

        String digitsList = "0123456789";
        int codeLen = 4;

        String code = random.ints(codeLen, 0, digitsList.length())
                .mapToObj(digitsList::charAt)
                .map(Objects::toString)
                .collect(Collectors.joining())
        ;

        redisTemplate.opsForValue().set(key, code, Duration.ofSeconds(30));

        return code;
    }

    public boolean acceptCode(String email, String code){
        String key = keyPrefix + email;

        if(Objects.equals(redisTemplate.opsForValue().get(key), code)){
            redisTemplate.delete(key);
            System.out.println("You successfully logged in");
            return true;
        }

        return false;
    }
}