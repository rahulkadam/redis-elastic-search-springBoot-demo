package com.example.demo.service;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemcacheService {

    @Autowired
    public MemcachedClient memcachedClient;

    public Object storeMCValue(String key, String value) {
        try {
            memcachedClient.set(key, 0, value);
            memcachedClient.asyncIncr("count" , 1);
            return memcachedClient.get(key);
        } catch (Exception d) {
            return "Error memcached";
        }
    }

    public Object getMCValue(String key) {
        try {
            return memcachedClient.get(key);
        } catch (Exception e) {
            return "Error Occure in memcached";
        }
    }

    public Object getMCCount() {
        try {
            return memcachedClient.get("count");
        } catch (Exception e) {
            return "Error Occure in memcached";
        }
    }

}
