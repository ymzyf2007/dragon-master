package com.dragon.cache.redis.jedis.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseRedisDao<K, V> {
	
	@Autowired(required = true)
    protected RedisTemplate<K,V> redisTemplate;

}