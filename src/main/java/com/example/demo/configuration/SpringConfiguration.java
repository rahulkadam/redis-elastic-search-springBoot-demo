package com.example.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.ClientConfig;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring configuration for profile Dev. This should accept argument from dev application resource
 * application-dev.properties
 */
@Configuration
@Profile("dev")
public class SpringConfiguration {

    @Value("${elastic.url}")
    private String connectionUrl;

    @Value("${jedis.hostname}")
    private String jedisHostname;
    @Value("${jedis.password}")
    private String jedisPassword;
    @Value("${jedis.port}")
    private Integer jedisPort;

    @Value("${memcache.servername}")
    private String memCacheServerName;
    @Value("${memcache.password}")
    private String memCachePassword;
    @Value("${memcache.username}")
    private String memCacheUserName;

    /**
     * should create bean from this if property PivotalEnv is present and it's value is local
     * we do not require it now as we are using maven bases profile
     * @return
     * @throws Exception
     */
    @Bean
    @ConditionalOnProperty(name = "PivotalEnv", havingValue = "local")
    public JestHttpClient jestClientLocal() throws Exception {
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
    @ConditionalOnProperty(name = "PivotalEnv", havingValue = "local")
    public JedisPool redisClient() throws Exception {
        return new JedisPool(new JedisPoolConfig(),
                jedisHostname,
                jedisPort,
                Protocol.DEFAULT_TIMEOUT,
                jedisPassword);
    }

    @Bean
    @ConditionalOnProperty(name = "PivotalEnv", havingValue = "local")
    public MemcachedClient memCachedClient() throws Exception {

        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"},
                new PlainCallbackHandler(memCacheUserName, memCachePassword));

        return new MemcachedClient(
                new ConnectionFactoryBuilder()
                        .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                        .setAuthDescriptor(ad).build(),
                AddrUtil.getAddresses(memCacheServerName));
    }

}
