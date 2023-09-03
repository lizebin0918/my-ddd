package com.lzb.infr.order.repository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lzb.BaseIntegrationTest;
import com.lzb.domain.order.Order;
import com.lzb.domain.order.OrderRepository;
import com.lzb.infr.event.persistence.DomainEventPo;
import com.lzb.infr.event.persistence.service.DomainEventPoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import org.springframework.test.context.jdbc.Sql;

import static java.time.Instant.ofEpochMilli;

class OrderRepositoryDbImplIntegrationTest extends BaseIntegrationTest {

    @Resource(name = OrderRepositoryDbImpl.BEAN_NAME)
    private OrderRepository orderRepository;

    @Resource
    private DomainEventPoService domainEventPoService;

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

    @Test
    @Sql("/sql/OrderRepositoryDbImplIntegrationTest/should_update_order_info.sql")
    @DisplayName("测试聚合根查询")
    void should_update_order_info() {
        long orderId = 1L;
        Order order = orderRepository.get(orderId).orElseThrow();
        order.updateTotalActualPay(new BigDecimal("8888"));
        orderRepository.update(order);
        Order newOrder = orderRepository.get(orderId).orElseThrow();
        assertJSON(newOrder);
    }

    @Test
    @DisplayName("测试取消订单")
    @Sql("/sql/OrderRepositoryDbImplIntegrationTest/should_cancel_order.sql")
    void should_cancel_order() {
        // given
        long orderId = 1L;
        Order order = orderRepository.get(orderId).orElseThrow();
        Clock constantClock = Clock.fixed(ofEpochMilli(0), ZoneId.systemDefault());
        try (MockedStatic<Instant> instant = mockStatic(Instant.class)) {
            instant.when(() -> Instant.now()).thenReturn(constantClock.instant());
            order.cancel();
        }

        // when
        orderRepository.update(order);

        // then
        Order newOrder = orderRepository.get(orderId).orElseThrow();
        List<DomainEventPo> events = domainEventPoService.list();

        var assertMap = new HashMap<>();
        assertMap.put("order", newOrder);
        assertMap.put("events", events);

        assertJSON(assertMap);
    }

}