package com.lzb;

import java.time.Duration;
import java.time.Instant;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.lzb.infr.config.mybatis.MybatisPlusConfig;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.TestPropertySourceUtils;

@Slf4j
@Testcontainers
@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringJUnitConfig(classes = {MybatisPlusConfig.class}, initializers = BaseMapperTest.DataSourceInitializer.class)
@TestPropertySource(locations = "classpath:application-addition.properties")
public abstract class BaseMapperTest extends BaseTest {


    ///////////////////////////////////////////////////////////////////////////
    // docker
    ///////////////////////////////////////////////////////////////////////////

    @Container
    public static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:14.9")
            .withAccessToHost(true)
            .withPrivilegedMode(true)
            .withUsername("postgres")
            .withPassword("123456")
            ;

    static class DataSourceInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgreSQLContainer<?> database = BaseMapperTest.database;
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }

    static {

        Instant start = Instant.now();
        database.start();
        log.info("----------------------------------------------------------------------");
        log.info("TestContainers 启动耗时 {} 秒", Duration.between(start, Instant.now()).getSeconds());
        log.info("----------------------------------------------------------------------");

    }

}