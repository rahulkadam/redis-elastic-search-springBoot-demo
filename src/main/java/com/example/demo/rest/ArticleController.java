package com.example.demo.rest;

import com.example.demo.ElasticData;
import com.example.demo.elastic.Article;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    public ElasticData elasticData;

    public List<Article> articleListFallback() {
        return new ArrayList<>();
    }

    public Article articleFallback() {
        return new Article();
    }

    public String fallback() {
        return "Not able to create Index. Error Occured";
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.POST , value = "/createIndex")
    public String createIndex(@RequestParam  Map<String , String> map) {
        try {
            elasticData.createIndex(map.get("name"));
        } catch (Exception e) {
            return map.get("name") + "Index has not been failed" + " " +e.getLocalizedMessage();
        }
        return map.get("name") + "Index has been created Successfully";
    }

    @HystrixCommand(fallbackMethod = "articleFallback")
    @RequestMapping(method = RequestMethod.POST , value = "/create")
    public Article createArticle(@RequestParam Article article) {
        try {
            return elasticData.createArticle(article);
        } catch (Exception e) {
            return new Article();
        }
    }

    @HystrixCommand(fallbackMethod = "articleListFallback")
    @RequestMapping(method = RequestMethod.GET , value = "/search/{query}")
    public List<Article> searchArticle(@PathVariable String query) {
        try {
            return elasticData.searchArticle(query);
        } catch (Exception e) {
            return new ArrayList<Article>();
        }
    }
}
