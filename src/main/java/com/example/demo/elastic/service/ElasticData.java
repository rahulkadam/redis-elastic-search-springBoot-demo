package com.example.demo.elastic.service;

import com.example.demo.service.RedisService;
import com.example.demo.dto.Product;
import com.example.demo.elastic.Article;
import com.example.demo.util.stats.RecordTime;
import io.searchbox.client.JestResult;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticData {


    @Autowired
    private JestHttpClient jestClient;

    @Autowired
    RedisService redisService;

    public void createIndex(String indexName) throws Exception {
        jestClient.execute(new CreateIndex.Builder(indexName).build());
    }

    public Article createArticle(String author , String content) throws Exception{
        Article article = new Article(author , content);
        Index index = new Index.Builder(article).index("articles").type("article").build();
        jestClient.execute(index);
        return article;
    }

    public Article createArticle(Article article) throws Exception{
        Index index = new Index.Builder(article).index("article").type("article").build();
        jestClient.execute(index);
        return article;
    }

    public List<Article> searchArticle(String query) throws Exception{
        String data = "\"query\": {\"bool\": { \"must\": [{ \"match\": { \"message\": "+ query+" } }]}}})";
        String query1 = "{\n" +
                "    \"query\": {\n" +
                "\"match\" : { "+
                "\"author\" : \""+ query + "\""+
                " }\n" +
                " }\n" +
                "}";

        Search search = (Search) new Search.Builder(query1)
                .addIndex("article")
                .addType("article")
                .build();
        JestResult result =  jestClient.execute(search);
        return (List<Article>)result.getSourceAsObjectList(Article.class);
    }

    @RecordTime
    public Product addProduct(Product product) throws Exception {
        Index index = new Index.Builder(product).index("article").type("article").build();
        jestClient.execute(index);
        return product;
    }
    public List<Product> searchProduct(String query) throws Exception{
        String data = "\"query\": {\"bool\": { \"must\": [{ \"match\": { \"message\": "+ query+" } }]}}})";
        String query1 = "{\n" +
                "    \"query\": {\n" +
                "\"match\" : { "+
                "\"name\" : \""+ query + "\""+
                " }\n" +
                " }\n" +
                "}";

        Search search = (Search) new Search.Builder(query1)
                .addIndex("article")
                .addType("article")
                .build();
        JestResult result =  jestClient.execute(search);
        return (List<Product>)result.getSourceAsObjectList(Product.class);
    }
}
