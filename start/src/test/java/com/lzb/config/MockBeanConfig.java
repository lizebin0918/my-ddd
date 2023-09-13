package com.lzb.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.lzb.adapter.rpc.inverntory.InventoryClient;
import com.lzb.annotation.MockBeanInit;
import com.lzb.component.helper.TransactionHelper;
import com.lzb.component.id.IdGenerator;
import org.jetbrains.annotations.NotNull;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * 定义MockBean默认行为
 * 采用声明式的，避免多个Test都写 @MockBean/@SpyBean，Test直接自动注入即可<br/>
 * @author lizebin
 */
@TestConfiguration
@SpyBeans({
        @SpyBean(TransactionHelper.class),
        @SpyBean(IdGenerator.class),
        @SpyBean(InventoryClient.class)
})
@MockBeans({

})
public class MockBeanConfig implements BeanFactoryPostProcessor, ApplicationContextAware {

    ///////////////////////////////////////////////////////////////////////////
    // MockBean配置
    ///////////////////////////////////////////////////////////////////////////

    public static void initMockBean(ConfigurableListableBeanFactory beanFactory) {
        List<Method> mockInitMethod = Arrays.stream(MockBeanConfig.class.getDeclaredMethods())
                .filter(x -> x.getAnnotation(MockBeanInit.class) != null).toList();
        for (Method method : mockInitMethod) {
            Object bean = checkAndGetMockBean(beanFactory, method);
            invokeMockInitMethod(method, bean);
        }
    }

    private static void invokeMockInitMethod(Method method, Object bean) {
        try {
            method.invoke(null, bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private static Object checkAndGetMockBean(ConfigurableListableBeanFactory beanFactory, Method method) {
        Assert.isTrue(Modifier.isStatic(method.getModifiers()), "@MockBeanInit方法必须为static");
        Class<?>[] parameterTypes = method.getParameterTypes();
        Assert.isTrue(parameterTypes.length == 1, "@MockBeanInit方法的参数只能为1个");
        Object bean = beanFactory.getBean(parameterTypes[0]);
        MockingDetails mockingDetails = Mockito.mockingDetails(bean);
        Assert.isTrue(mockingDetails.isMock() || mockingDetails.isSpy(), "只能给@MockBean设置默认行为");
        return bean;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 初始化MockBean行为
    ///////////////////////////////////////////////////////////////////////////

    private static ApplicationContext applicationContext;

    /*@MockBeanInit
    private static void beforeEachListLogisticsTrackingDTOForC(Client frontHadesClient) {
        Response<RpcLogisticsTrackingDTO> response = JsonUtil.json2JavaBean("", new TypeReference<>() { });
        doReturn(response).when(frontHadesClient).listLogisticsTrackingDTOForC(anyString());
    }*/

    ///////////////////////////////////////////////////////////////////////////
    // 初始化SpyBean行为
    ///////////////////////////////////////////////////////////////////////////

    public static void initSpyBean() {
        beforeAllTransactionHelper();
    }

    /**
     * 测试过程中，默认事务回滚，无法执行事务提交的回调方法，直接mock掉
     */
    public static void beforeAllTransactionHelper() {
        Assert.notNull(applicationContext, "容器不能为空");
        // 直接执行
        doAnswer((invocation) -> {
            ((Runnable) invocation.getArgument(0)).run();
            return null;
        }).when(applicationContext.getBean(TransactionHelper.class)).runAfterCommit(any(Runnable.class));

        // 异步改同步执行
        doAnswer((invocation) -> {
            ((Runnable) invocation.getArgument(0)).run();
            return null;
        }).when(applicationContext.getBean(TransactionHelper.class)).asyncRunAfterCommit(any(Runnable.class));
    }

    /**
     * 提前给mockBean设置行为，避免容器启动过程中出错
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 创建bean之前，bean容器刷新的时候调用
        initMockBean(beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
