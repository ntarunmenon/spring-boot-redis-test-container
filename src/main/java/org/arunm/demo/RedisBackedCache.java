package org.arunm.demo;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisBackedCache {

    private StringRedisTemplate redisTemplate;

    public RedisBackedCache(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void put(String key, String value) {
        redisTemplate.opsForSet()
                .add(key,value);
    }

    public String get(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

}
