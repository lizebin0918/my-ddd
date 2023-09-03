package com.lzb.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.cache.CacheManager;

/**
 * 定义MockBean默认行为
 * 采用声明式的，避免多个Test都写 @MockBean/@SpyBean，Test直接自动注入即可<br/>
 * @author lizebin
 */
@TestConfiguration
@SpyBeans({
    @SpyBean(CacheManager.class)
})
public class MockBeanConfig {

}
