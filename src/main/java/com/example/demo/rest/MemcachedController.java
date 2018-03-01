package com.example.demo.rest;

import com.example.demo.cache.MemcacheService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memcache")
public class MemcachedController {
    @Autowired
    public MemcacheService memcacheService;

    public String fallback() {
        return "Not found. Error Occured";
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET , value="setmc/{key}/{value}")
    public Object setMCValue(@PathVariable String key , @PathVariable String value){
        return memcacheService.storeMCValue(key, value);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET , value = "/getmc/{key}")
    public Object getMCValue(@PathVariable String key) {
        return memcacheService.getMCValue(key);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET , value = "/countmc")
    public Object getMCCount() {
        return memcacheService.getMCCount();
    }

}
