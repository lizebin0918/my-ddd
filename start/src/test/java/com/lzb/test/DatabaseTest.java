package com.lzb.test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.lzb.BaseIntegrationTest;
import com.lzb.infr.order.entity.Order;
import com.lzb.infr.order.mapper.OrderMapper;
import com.lzb.infr.order.service.IOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-08-28 23:31
 * @author mac
 */
class DatabaseTest extends BaseIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    @DisplayName("测试数据库连接")
    void should_connect_datasource() throws SQLException {

        Connection connection = dataSource.getConnection();
        Assertions.assertNotNull(connection);

        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from order_detail");
        Assertions.assertTrue(execute);

    }

    @Test
    @DisplayName("测试订单条数")
    void should_count_from_order() {
        assertThat(orderService.count()).isGreaterThanOrEqualTo(0);

        Order order = new Order();
        order.setOrderStatus("WAIT_AUTH");
        order.setCurrency("CNY");
        order.setExchangeRate(BigDecimal.ZERO);
        order.setTotalShouldPay(BigDecimal.ZERO);
        order.setTotalActualPay(BigDecimal.ZERO);
        order.setAddTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        boolean a = orderService.save(order);
        assertThat(a).isTrue();

        long orderId = order.getOrderId();
        Order order1 = new Order();
        order1.setOrderId(orderId);
        order1.setOrderStatus("WAIT_AUTH");
        order1.setCurrency("CNY");
        order1.setAddTime(LocalDateTime.now());
        order1.setUpdateTime(LocalDateTime.now());
        boolean updateFlag = orderService.updateById(order1);
        assertThat(updateFlag).isTrue();

        Order o = orderService.getById(orderId);
        assertThat(o.getTotalShouldPay()).isNull();

    }

}
