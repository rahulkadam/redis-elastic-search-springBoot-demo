package com.example.demo;

import com.example.demo.elastic.Article;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticData {

    @Autowired
    private JestClient jestClient;

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
        Index index = new Index.Builder(article).index("articles").type("article").build();
        jestClient.execute(index);
        return article;
    }
    public List<Article> searchArticle(String query) throws Exception{
        Search search = (Search) new Search.Builder(query)
                .addIndex("articles")
                .addType("article").build();
        JestResult result =  jestClient.execute(search);
        return (List<Article>)result.getSourceAsObject(Article.class);
    }
}
