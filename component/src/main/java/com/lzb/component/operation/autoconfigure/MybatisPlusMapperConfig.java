package com.lzb.component.operation.autoconfigure;

import com.lzb.component.operation.strategy.impl.OperationLogMapper;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.context.annotation.Configuration;

@MapperScan(basePackageClasses = OperationLogMapper.class)
@Configuration
public class MybatisPlusMapperConfig {
}
