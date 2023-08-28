package com.lzb;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	@Bean
	public DataSource dataSource() throws SQLException {
		System.out.println("初始化datasource");
		DataSource dataSource = DataSourceBuilder.create().build();
		System.out.println(dataSource.getConnection());
		return dataSource;
	}

}