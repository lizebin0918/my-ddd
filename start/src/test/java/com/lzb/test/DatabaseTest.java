package com.lzb.test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.lzb.BaseIntegrationTest;
import com.lzb.infr.order.entity.OrderDo;
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
    @Test
    @DisplayName("测试数据库连接")
    void should_connect_datasource() throws SQLException {

        Connection connection = dataSource.getConnection();
        Assertions.assertNotNull(connection);

        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from order_detail");
        Assertions.assertTrue(execute);

    }

}
