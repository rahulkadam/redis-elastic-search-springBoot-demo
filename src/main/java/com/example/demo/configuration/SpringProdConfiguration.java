package com.example.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Profile("release")
public class SpringProdConfiguration {

    @Bean
    @ConditionalOnProperty(name = "PivotalEnv", havingValue = "production")
    public JestHttpClient jestClientProd() throws Exception {
        Map result = new ObjectMapper().readValue(System.getenv("VCAP_SERVICES"), HashMap.class);
        String connectionUrl = (String) ((Map) ((Map) ((List)
                result.get("searchly")).get(0)).get("credentials")).get("uri");
        HttpClientConfig clientConfig = new HttpClientConfig.
                Builder(connectionUrl)
                .multiThreaded(true)
                .build();

        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(clientConfig);
        return (JestHttpClient) factory.getObject();
    }

    @Bean
    @ConditionalOnProperty(name = "PivotalEnv", havingValue = "production")
    public JedisPool redisClientProd() throws Exception {
        Map result = new ObjectMapper().readValue(System.getenv("VCAP_SERVICES"), HashMap.class);
        Map redisCacheMap = ((Map) ((Map) ((List) result.get("rediscloud")).get(0)).get("credentials"));

        String hostname = (String) redisCacheMap.get("hostname");
        String password = (String) redisCacheMap.get("password");
        Integer port = Integer.parseInt((String) redisCacheMap.get("port"));
        return new JedisPool(new JedisPoolConfig(),
                hostname,
                port,
                Protocol.DEFAULT_TIMEOUT,
                password);
    }

    @Bean
    @ConditionalOnProperty(name = "PivotalEnv", havingValue = "production")
    public MemcachedClient memCachedClientProd() throws Exception {

        Map result = new ObjectMapper().readValue(System.getenv("VCAP_SERVICES"), HashMap.class);
        Map memcacheMap = ((Map) ((Map) ((List) result.get("memcachedcloud")).get(0)).get("credentials"));
        String serverName = (String) memcacheMap.get("servers");
        String username = (String) memcacheMap.get("username");
        String password = (String) memcacheMap.get("password");
        // Initialize memcached Pool
        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                new PlainCallbackHandler(username, password));

        return new MemcachedClient(
                new ConnectionFactoryBuilder()
                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                        .setAuthDescriptor(ad).build(),
                AddrUtil.getAddresses(serverName));
    }
}
