package com.example.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringConfiguration {

    @Bean
    public JestHttpClient jestClient() throws Exception {
       // Map result = new ObjectMapper().readValue(System.getenv("VCAP_SERVICES") , HashMap.class);
        // http://gopivotal:5dc97fd3a1340f8690d9f3686a26c25a@thorin-us-east-1.searchly.com"
        String connectionUrl = "http://gopivotal:5dc97fd3a1340f8690d9f3686a26c25a@thorin-us-east-1.searchly.com"; //(String)((Map) ((Map) ((List)
 //               result.get("searchly")).get(0)).get("credentials")).get("uri");
        HttpClientConfig clientConfig = new HttpClientConfig.
                Builder(connectionUrl)
                .multiThreaded(true)
                .build();

        // Construct a new Jest client according to configuration via factory
            JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        return (JestHttpClient)factory.getObject();
    }
}
