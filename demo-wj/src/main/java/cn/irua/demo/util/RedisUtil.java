package cn.irua.demo.util;


import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
 
@Component
public class RedisUtil {
 
    @Resource
    private RedisTemplate<String, String> redisTemplate;
 
    public void set(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }
 
    public void setex(String key, String value, int seconds) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, seconds,TimeUnit.HOURS);
    }
    
    public String get(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
       return valueOperations.get(key);
    }
    public Boolean remove(String key) {
        return redisTemplate.delete(key);
    }
}
