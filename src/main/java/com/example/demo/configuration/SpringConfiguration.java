package com.example.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringConfiguration {

    @Bean
    public JestClient jestClient() throws Exception {
        Map result = new ObjectMapper().readValue(System.getenv("VCAP_SERVICES") , HashMap.class);


        String connectionUrl = (String)((Map) ((Map) ((List)
                result.get("searchly")).get(0)).get("credentials")).get("uri");
        ClientConfig clientConfig = new ClientConfig.Builder(connectionUrl).multiThreaded(true).build();

        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        factory.setClientConfig(clientConfig);
        return factory.getObject();
    }
}
