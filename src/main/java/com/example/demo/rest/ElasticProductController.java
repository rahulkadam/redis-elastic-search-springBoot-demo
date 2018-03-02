package com.example.demo.rest;

import com.example.demo.service.RedisService;
import com.example.demo.dto.Product;
import com.example.demo.elastic.service.ElasticData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ElasticProductController {
    @Autowired
    public ElasticData elasticData;

    @Autowired
    RedisService redisService;

    public List<Product> articleListFallback() {
        return new ArrayList<>();
    }

    public Product articleFallback() {
        return new Product();
    }

    public String fallback() {
        return "Not able to create Index. Error Occured";
    }


    @HystrixCommand(fallbackMethod = "articleFallback")
    @RequestMapping(method = RequestMethod.POST , value = "/add")
    public Product addProduct(@RequestBody Product product) {
        try {
            return redisService.addProduct(product);
        } catch (Exception e) {
            return new Product();
        }
    }


    @RequestMapping(method = RequestMethod.GET , value = "/get/{query}")
    public Product getProduct(@PathVariable String query) {
        try {
            return redisService.getProduct(query);
        } catch (Exception e) {
            return new Product();
        }
    }

    @HystrixCommand(fallbackMethod = "articleListFallback")
    @RequestMapping(method = RequestMethod.GET , value = "/search/{query}")
    public List<Product> searchArticle(@PathVariable String query) {
        try {
            return elasticData.searchProduct(query);
        } catch (Exception e) {
            return new ArrayList<Product>();
        }
    }
}
