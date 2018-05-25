package com.example.demo.elastic.service;

import com.example.demo.dto.stats.Message;
import com.example.demo.elastic.Article;
import com.example.demo.service.RedisService;
import com.example.demo.service.WhatsappMessageReader;
import com.google.gson.JsonObject;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestResult;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.indices.CreateIndex;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WhatsAppData {
    @Autowired
    private JestHttpClient jestClient;

    @Autowired
    public JedisPool jedisPool;

    @Autowired
    RedisService redisService;

    @Autowired
    private WhatsappMessageReader whatsappMessageReader;

    private String indexName = "stats";
    private String indexType = "whatsapp";

    public void createIndex() throws Exception {
        jestClient.execute(new CreateIndex.Builder(indexName).build());
    }

    /**
     *
     * @param in
     * @throws Exception
     */
    public void loadWhatsAppData(InputStream in) throws Exception {
        List<Message> messages = whatsappMessageReader.readMessage(in);
        saveMessage(messages);
    }

    /**
     *
     * @param messages
     * @throws Exception
     */
    public void saveMessage(List<Message> messages) throws Exception {
        List<BulkableAction> bulk = new ArrayList<>();
        messages.stream().forEach(message -> {
            bulk.add(createBulksAction(message));
        });
        Bulk bulkRequest = new Bulk.Builder().addAction(bulk).build();
        JestResult result = jestClient.execute(bulkRequest);
        if(result.getErrorMessage() != null) {
            throw new Exception(result.getErrorMessage());
        }
    }


    private BulkableAction createBulksAction(Message message) {
        return new Index.Builder(message).index(indexName).type(indexType).id(String.valueOf(message.getId())).build();
    }

    /**
     * @param message
     * @return
     * @throws IOException
     */
    public Message saveMessage(Message message) throws Exception {
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        saveMessage(messages);
        return message;
    }

    public List<Message> list() throws Exception {
        Search search = (Search) new Search.Builder(buildBasicQuery(QueryBuilders.matchAllQuery()))
                .addIndex(indexName)
                .addType(indexType)
                .build();
        JestResult result =  jestClient.execute(search);
        return (List<Message>)result.getSourceAsObjectList(Message.class);
    }

    public Map<String , Long> aggregationByUser() throws Exception {
        Search search = (Search) new Search.Builder(aggregationBasicQuery("user" , "user.name"))
                .addIndex(indexName)
                .addType(indexType)
                .build();
        SearchResult result =  jestClient.execute(search);

        List<TermsAggregation.Entry> entryList = result.getAggregations().getTermsAggregation("user").getBuckets();
        Map<String , Long> map = entryList.stream()
                .collect(Collectors.toMap(a -> a.getKey() , b -> b.getCount()));
        return map;
    }

    public String buildBasicQuery(QueryBuilder builder) {
        return " { \"query\": " + builder.toString() +" } ";
    }

    public String aggregationBasicQuery(String term, String field) {
        /*
         */
        return " { \"aggs\":  {" +
                "             \"" + term + "\": {" +
                "              \"terms\" : { \"field\": \"" + field + "\" } } " +
                "     }}";
    }

}
