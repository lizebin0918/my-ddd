package com.lzb.component.redisson;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Data
@Configuration
@ConfigurationProperties(prefix = "redisson")
public class RedissonConfig {

    private String host;

    private int port = 6379;

    private int nettyThreads = 16;

    private int threads = 8;


    @Bean
    public RedissonClient redisson() {
        Assert.notNull(host, "host must not be null");
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.setNettyThreads(nettyThreads);
        config.setThreads(threads);
        return Redisson.create(config);
    }
}
