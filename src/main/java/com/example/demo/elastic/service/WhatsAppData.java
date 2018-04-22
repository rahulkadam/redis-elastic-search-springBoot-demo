package com.example.demo.elastic.service;

import com.example.demo.dto.stats.Message;
import com.example.demo.elastic.Article;
import com.example.demo.service.RedisService;
import com.example.demo.service.WhatsappMessageReader;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        messages.stream().forEach(message -> {
            try {
                saveMessage(message);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     *
     * @param message
     * @return
     * @throws IOException
     */
    public Message saveMessage(Message message) throws IOException {
        Index index = new Index.Builder(message).index(indexName).type(indexType).build();
        jestClient.execute(index);
        return message;
    }
}
