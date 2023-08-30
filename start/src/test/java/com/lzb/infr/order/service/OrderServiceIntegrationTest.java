package com.lzb.infr.order.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.lzb.BaseIntegrationTest;
import com.lzb.component.utils.date.DateUtils;
import com.lzb.infr.order.entity.OrderDo;
import com.lzb.infr.order.mapper.OrderMapper;
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

}
