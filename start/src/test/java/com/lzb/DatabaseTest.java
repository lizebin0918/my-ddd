package com.lzb;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <br/>
 * Created on : 2023-08-28 23:31
 * @author mac
 */
@SpringBootTest(classes = MyDddApplication.class)
class DatabaseTest extends BaseDockerTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("测试数据库连接")
    void should_() throws SQLException {
        Connection connection = dataSource.getConnection();
        Assertions.assertNotNull(connection);
    }

}
