package com.lzb;

import com.lzb.config.MockBeanConfig;
import com.lzb.config.TestConfig;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br/>
 * Created on : 2023-08-29 14:01
 * @author lizebin
 */
@Transactional
@TestPropertySource(locations = "classpath:application-addition.properties")
@SpringBootTest(classes = Application.class)
@Import({TestConfig.class, MockBeanConfig.class})
public abstract class BaseIntegrationTest extends BaseDockerTest {

}
