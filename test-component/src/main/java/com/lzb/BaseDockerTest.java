package com.lzb;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import com.redis.testcontainers.RedisContainer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.GenericContainer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;

@Slf4j
@Testcontainers(parallel = true)
@ContextConfiguration(initializers = {
        BaseDockerTest.DataSourceInitializer.class,
        BaseDockerTest.RedisInitializer.class
})
public abstract class BaseDockerTest extends BaseTest {


    ///////////////////////////////////////////////////////////////////////////
    // docker相关
    ///////////////////////////////////////////////////////////////////////////

    public static final PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:14.9")
            .withAccessToHost(true)
            .withPrivilegedMode(true)
            .withUsername("postgres")
            .withPassword("123456")
            ;

    public static final RedisContainer redis =
            new RedisContainer(DockerImageName.parse("redis:7.2.0"))
                    .withExposedPorts(6379);

    static class DataSourceInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PostgreSQLContainer<?> database = BaseDockerTest.database;
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + database.getJdbcUrl(),
                    "spring.datasource.username=" + database.getUsername(),
                    "spring.datasource.password=" + database.getPassword()
            );
        }
    }

    static class RedisInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            RedisContainer redis = BaseDockerTest.redis;
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.data.redis.host=" + redis.getHost(),
                    "spring.data.redis.port=" + redis.getFirstMappedPort()
            );
        }
    }

    static {

        Instant start = Instant.now();
        Stream.of(database, redis).parallel().forEach(GenericContainer::start);
        log.info("----------------------------------------------------------------------");
        log.info("TestContainers 启动耗时 {} 秒", Duration.between(start, Instant.now()).getSeconds());
        log.info("----------------------------------------------------------------------");

    }

    @AfterAll
    public static void afterAll(TestInfo testInfo) {
    }

    /**
     * 每个test类都会执行一次
     */
    @BeforeAll
    public static void beforeAll(TestInfo testInfo) {
    }

    /**
     * 每个方法执行之前执行一次
     */
    @BeforeEach
    public void beforeEach() {
    }

}