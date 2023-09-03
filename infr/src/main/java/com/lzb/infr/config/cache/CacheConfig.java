package com.lzb.infr.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.lzb.infr.config.cache.CacheConstants.ORDER;

/**
 * <br/>
 * Created on : 2023-09-03 12:15
 * @author mac
 */
@EnableCaching
@Configuration
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(ORDER);
    }

}
