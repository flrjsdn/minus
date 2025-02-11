package com.hexa.muinus.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisTestDataLoader {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisTestDataLoader(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void loadTestData() {
        HashOperations<String, String, Integer> hashOps = redisTemplate.opsForHash();
        String redisKey = "preference:search:count";  // Redis 해시 키

        // 테스트 데이터 (userNo:itemId => searchCount)
        Map<String, Integer> testData = Map.of(
                "1:1", 5,
                "1:2", 3,
                "2:1", 7,
                "2:13", 0,     // 검색 횟수 0인 경우도 테스트
                "2:14", 12
        );

        // Redis에 데이터 삽입
        testData.forEach((key, value) -> {
            hashOps.put(redisKey, key, value);
            System.out.println("Inserted: " + key + " => " + value);
        });

        System.out.println("Redis 테스트 데이터 삽입 완료");
    }
}
