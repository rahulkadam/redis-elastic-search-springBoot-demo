package com.example.demo.cache;

import io.searchbox.client.JestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    public JedisPool jedisPool;

    public String storeValue(String key, String value) {
        try {
            Jedis jd = jedisPool.getResource();
            jd.set(key, value);
            jd.incr("count");
            return jd.get(key);
        } catch (Exception d) {
            return "Error Occure";
        }
    }

    public String getValue(String key) {
        try {
            Jedis jd = jedisPool.getResource();
            jd.incr("count");

            String redisvalue = jd.get(key);
            String value = redisvalue == null ? "" : redisvalue;
            return value;
        } catch (Exception e) {
            return "Error Occure";
        }
    }

    public String getCount() {
        try {
            Jedis jd = jedisPool.getResource();
            String redisvalue = jd.get("count");
            return redisvalue;
        } catch (Exception e) {
            return "Error Occure";
        }
    }
}
