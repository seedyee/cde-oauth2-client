package io.cde.oauth2.client.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by liaofangcai.
 * Created time 12/12/16.
 */
@Component
public class RedisRepository {

    /**
     * Redis Template 对象.
     */
    @Autowired
    private StringRedisTemplate template;

    /**
     * 从redis中获取值.
     * @param key this key.
     * @return this value.
     */
    public String get(final String key) {
        return template.opsForValue().get(key);
    }

    /**
     *
     * 存储数据到redis中.
     * @param key this key.
     * @param value this value.
     * @param timeToLiveSeconds this timeToLiveSeconds
     */
    public void set(final String key, final String value, final long timeToLiveSeconds) {
        template.opsForValue().set(key, value, timeToLiveSeconds, TimeUnit.SECONDS);
    }
}
