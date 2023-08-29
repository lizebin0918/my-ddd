package com.lzb;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

/**
 * <br/>
 * Created on : 2023-08-29 14:01
 * @author lizebin
 */
@Transactional
@TestPropertySource(locations = "classpath:application-addition.properties")
@SpringBootTest(classes = MyDddApplication.class)
public abstract class BaseIntegrationTest extends BaseDockerTest {

}
