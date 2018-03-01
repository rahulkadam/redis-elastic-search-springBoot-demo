package com.example.demo.rest;

import com.example.demo.cache.RedisService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisCacheController {

    @Autowired
    public RedisService redisService;

    public String fallback() {
        return "Not found. Error Occured";
    }
    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET , value="/set/{key}/{value}")
    public String setValue(@PathVariable String key , @PathVariable String value){
        return redisService.storeValue(key, value);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET , value = "/get/{key}")
    public String getValue(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET , value = "count")
    public String getCount() {
        return redisService.getCount();
    }
}
