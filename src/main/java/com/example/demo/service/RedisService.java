package com.example.demo.service;

import com.example.demo.dto.Product;
import com.example.demo.util.RedisSerializer;
import com.example.demo.util.stats.RecordTime;
import com.example.demo.util.stats.TimeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Service to store and fetch data from Redis.
 */
@Service
public class RedisService {

    @Autowired
    public JedisPool jedisPool;

    @Autowired
    RedisSerializer redisSerializer;

    /**
     * Store String value to Redis
     * @param key
     * @param value
     * @return
     */
    public String storeValue(String key, String value) {
        try {
            Jedis jd = jedisPool.getResource();
            jd.set(key, value);
            jd.incr("count");
            return jd.get(key);
        } catch (Exception d) {
            return "Error Occure" + d.getMessage();
        }
    }

    /**
     * Fetch String value from Redis
     * @param key
     * @return
     */
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


    /**
     * Fetch List of keys value from Redis
     * @return
     */
    public Set<String> getKeys() {
        try {
            Jedis jd = jedisPool.getResource();
            return jd.keys("*");
        } catch (Exception e) {
            return new TreeSet<>();
        }
    }

    /**
     * To count no of times the set and get of string method is called. this return integer count from key "count"
     * @return
     */
    @RecordTime
    public String getCount() {
        try {
            Jedis jd = jedisPool.getResource();
            String redisvalue = jd.get("count");
            checkingParamAnotation(redisvalue);
            return redisvalue;
        } catch (Exception e) {
            return "Error Occure";
        }
    }


    /**
     * To store product into Redis. We need to serialize object for storing it into Redis
     * @param product
     * @return
     * @throws Exception
     */
    @RecordTime
    public Product addProduct(Product product) throws Exception{
        Jedis jd = jedisPool.getResource();
        jd.set(redisSerializer.convertToByte("recentProductList") , redisSerializer.convertToByte(product));
        return product;
    }

    /**
     * fetching Product object from Redis. Need to deSerialize byte into Product object
     * @param key
     * @return
     * @throws Exception
     */
    @RecordTime
    public Product getProduct(String key) throws Exception{
        key = key != null ? key : "recentProductList";
        Jedis jd = jedisPool.getResource();
        byte[] bytes = jd.get(redisSerializer.convertToByte(key));
        return (Product)redisSerializer.convertToObject(bytes);
    }

    @RecordTime
    private void checkingParamAnotation(@TimeParam(value = "str") String str) {
        System.out.println("Executed Filed param : " + str);
    }
}
