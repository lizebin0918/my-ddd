package com.lzb.infr.order.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzb.BaseIntegrationTest;
import com.lzb.component.utils.date.DateUtils;
import com.lzb.infr.order.entity.OrderDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-08-30 08:34
 * @author mac
 */
class OrderServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private IOrderService orderService;

    @Test
    @DisplayName("测试订单条数")
    void should_count_from_order() {
        assertThat(orderService.count()).isGreaterThanOrEqualTo(0);
    }

    @Test
    @DisplayName("插入设置addTime和updateTime")
    void should_fill_add_time_and_update_time() {
        OrderDo orderDo = new OrderDo();
        orderDo.setOrderStatus("WAIT_AUTH");
        orderDo.setCurrency("CNY");
        orderDo.setExchangeRate(BigDecimal.ZERO);
        orderDo.setTotalShouldPay(BigDecimal.ZERO);
        orderDo.setTotalActualPay(BigDecimal.ZERO);
        // 自动填充update_time/add_time
        boolean a = orderService.save(orderDo);
        assertThat(a).isTrue();

        OrderDo o = orderService.getById(orderDo.getOrderId());
        assertThat(o.getAddTime()).isNotNull();
        assertThat(o.getUpdateTime()).isNotNull();

    }

    @Test
    @DisplayName("update null")
    void should_update_null() {

        OrderDo orderDo = new OrderDo();
        orderDo.setOrderStatus("WAIT_AUTH");
        orderDo.setCurrency("CNY");
        orderDo.setExchangeRate(BigDecimal.ZERO);
        orderDo.setTotalShouldPay(BigDecimal.ZERO);
        orderDo.setTotalActualPay(BigDecimal.ZERO);
        boolean a = orderService.save(orderDo);
        assertThat(a).isTrue();

        long orderId = orderDo.getOrderId();
        LocalDateTime addTime = orderService.getById(orderId).getAddTime();
        LocalDateTime updateTime = orderService.getById(orderId).getUpdateTime();

        // 更新update_time，不更新add_time
        OrderDo orderDo1 = new OrderDo();
        orderDo1.setOrderId(orderId);
        orderDo1.setOrderStatus("WAIT_AUTH");
        orderDo1.setCurrency("CNY");
        boolean updateFlag = orderService.updateById(orderDo1);
        assertThat(updateFlag).isTrue();

        OrderDo o = orderService.getById(orderId);
        assertThat(o.getTotalShouldPay()).isNull();
        // addTime不变
        assertThat(DateUtils.toUnix(o.getAddTime())).isEqualTo(DateUtils.toUnix(addTime));
        // updateTime更新
        assertThat(DateUtils.toUnix(o.getUpdateTime())).isGreaterThanOrEqualTo(DateUtils.toUnix(updateTime));
    }

    @Test
    @DisplayName("批量更新自动填充update_time，不改变add_time，字段可以设置null")
    void should_batch_update() {

        batchInsert();

        List<OrderDo> list = orderService.list();
        Map<Long, Long> orderId2UpdateTime = new HashMap<>();
        Map<Long, Long> orderId2AddTime = new HashMap<>();

        for (OrderDo orderDo : list) {
            orderDo.setTotalShouldPay(null);
            orderDo.setExchangeRate(null);
            orderDo.setTotalActualPay(null);
            orderId2UpdateTime.put(orderDo.getOrderId(), DateUtils.toUnix(orderDo.getUpdateTime()));
            orderId2AddTime.put(orderDo.getOrderId(), DateUtils.toUnix(orderDo.getAddTime()));
        }

        orderService.updateBatchById(list);

        List<OrderDo> newList = orderService.list();
        for (OrderDo orderDo : newList) {
            assertThat(orderDo.getTotalShouldPay()).isNull();
            assertThat(orderDo.getExchangeRate()).isNull();
            assertThat(orderDo.getTotalActualPay()).isNull();
            assertThat(DateUtils.toUnix(orderDo.getAddTime())).isEqualTo(orderId2AddTime.get(orderDo.getOrderId()));
            assertThat(DateUtils.toUnix(orderDo.getUpdateTime())).isGreaterThanOrEqualTo(orderId2UpdateTime.get(orderDo.getOrderId()));
        }

    }

    private void batchInsert() {
        List<OrderDo> list = new ArrayList<>();

        OrderDo orderDo = new OrderDo();
        orderDo.setOrderStatus("WAIT_AUTH");
        orderDo.setCurrency("CNY");
        orderDo.setExchangeRate(BigDecimal.ZERO);
        orderDo.setTotalShouldPay(BigDecimal.ZERO);
        orderDo.setTotalActualPay(BigDecimal.ZERO);
        list.add(orderDo);

        OrderDo orderDo1 = new OrderDo();
        orderDo1.setOrderStatus("WAIT_AUTH");
        orderDo1.setCurrency("CNY");
        orderDo1.setExchangeRate(BigDecimal.ONE);
        orderDo1.setTotalShouldPay(BigDecimal.ONE);
        orderDo1.setTotalActualPay(BigDecimal.ONE);
        list.add(orderDo1);

        orderService.saveBatch(list);
    }

}
