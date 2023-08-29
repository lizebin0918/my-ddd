package com.lzb.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.lzb.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    void should_() throws SQLException {

        Connection connection = dataSource.getConnection();
        Assertions.assertNotNull(connection);

        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from order_detail");
        Assertions.assertTrue(execute);

    }

}
