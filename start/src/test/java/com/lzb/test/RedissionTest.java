package com.lzb.test;

import com.lzb.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-03 22:34
 * @author mac
 */
class RedissionTest extends BaseIntegrationTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    @DisplayName("测试redisson连接")
    void test_redisson() {
        assertThat(redissonClient.getBucket("a")).isNotNull();
    }


}
