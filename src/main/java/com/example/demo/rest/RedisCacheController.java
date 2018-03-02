package com.example.demo.rest;

import com.example.demo.cache.RedisService;
import com.example.demo.util.stats.RecordTime;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(RedisCacheController.class);


    public String fallback() {
        return "Not found. Error Occured";
    }

    @RecordTime
    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET, value = "/set/{key}/{value}")
    public String setValue(@PathVariable String key, @PathVariable String value) {
        logger.debug("set Redis Value :" + key + " : " + value);
        return redisService.storeValue(key, value);
    }

    @RecordTime
    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET, value = "/get/{key}")
    public String getValue(@PathVariable String key) {
        return redisService.getValue(key);
    }

    @RecordTime(number = 10)
    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET, value = "count")
    public String getCount() {
        return redisService.getCount();
    }
}
