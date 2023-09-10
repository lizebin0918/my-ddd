package com.lzb;

import com.lzb.config.MockBeanConfig;
import com.lzb.config.TestConfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
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
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestConfig.class, MockBeanConfig.class})
public abstract class BaseIntegrationTest extends BaseDockerTest implements InitializingBean, BeanFactoryAware {

    protected ConfigurableListableBeanFactory beanFactory;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 每个Test实例化执行一次
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {

        // {@link com.lzb.config.MockBeanConfig} 会执行一次mock行为，这是在容器启动过程中执行
        // 这里再执行一次，是为了在每个test方法执行之前，都能执行一次mock行为
        MockBeanConfig.initMockBean(beanFactory);

        MockBeanConfig.initSpyBean();

    }

}
