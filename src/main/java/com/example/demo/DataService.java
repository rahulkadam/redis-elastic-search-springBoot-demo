package com.example.demo;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Service
public class DataService {
    public static JedisPool pool = null;
    public static MemcachedClient mc = null;

    DataService() {
        try {
            System.out.println("Initialis pool");
            // Initializing redis Pool
            pool = new JedisPool(new JedisPoolConfig(),
                    "redis-18391.c10.us-east-1-4.ec2.cloud.redislabs.com",
                    18391,
                    Protocol.DEFAULT_TIMEOUT,
                    "vXvWXPmQs7CLRlAK");

            // Initialize memcached Pool
            AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
                    new PlainCallbackHandler("d4b8399f-40f5-4127-ab8b-7faa3332747e", "7xGiE2u2FGtZ2u87lH3pSzLULZjjU8UX"));

            mc = new MemcachedClient(
                    new ConnectionFactoryBuilder()
                            .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad).build(),
                    AddrUtil.getAddresses("memcached-11366.c13.us-east-1-3.ec2.cloud.redislabs.com:11366"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String storeValue(String key, String value) {
        try {
            Jedis jd = pool.getResource();
            jd.set(key, value);
            jd.incr("count");
            return jd.get(key);
        } catch (Exception d) {
            return "Error Occure";
        }
    }

    public String getValue(String key) {
        try {
            Jedis jd = pool.getResource();
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
            Jedis jd = pool.getResource();
            String redisvalue = jd.get("count");
            return redisvalue;
        } catch (Exception e) {
            return "Error Occure";
        }
    }
    public Object storeMCValue(String key, String value) {
        try {
            mc.set(key, 0, value);
            mc.asyncIncr("count" , 1);
            return mc.get(key);
        } catch (Exception d) {
            return "Error memcached";
        }
    }

    public Object getMCValue(String key) {
        try {
            return mc.get(key);
        } catch (Exception e) {
            return "Error Occure in memcached";
        }
    }

    public Object getMCCount() {
        try {
            return mc.get("count");
        } catch (Exception e) {
            return "Error Occure in memcached";
        }
    }
    public String values() {
        Jedis jd = pool.getResource();
        return jd.clientGetname();
    }
}


