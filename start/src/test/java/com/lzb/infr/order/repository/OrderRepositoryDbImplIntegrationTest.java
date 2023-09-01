package com.lzb.infr.order.repository;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lzb.BaseIntegrationTest;
import com.lzb.domain.order.Order;
import com.lzb.domain.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.jdbc.Sql;

class OrderRepositoryDbImplIntegrationTest extends BaseIntegrationTest {

    @Resource(name = OrderRepositoryDbImpl.BEAN_NAME)
    private OrderRepository orderRepository;

    @Test
    @Sql("/sql/OrderRepositoryDbImplIntegrationTest/should_order_get.sql")
    @DisplayName("测试聚合根查询")
    void should_order_get() {

        long orderId = 1L;
        Order order = orderRepository.get(orderId).orElseThrow();
        assertThat(order.snapshot()).isNotNull();

        Map<String, Order> excepted = new HashMap<>();
        excepted.put("current", order);
        excepted.put("snapshot", order.snapshot());

        assertJSON(excepted);
    }

}