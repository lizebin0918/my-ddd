package com.lzb.component.operation.autoconfigure;


import com.google.common.collect.Sets;
import com.lzb.component.operation.annotation.OperateLog;
import com.lzb.component.operation.annotation.OperateLogs;
import com.lzb.component.operation.aop.OperateLogAnnotationAdvisor;
import com.lzb.component.operation.aop.OperateLogAnnotationInterceptor;
import com.lzb.component.operation.context.OperationLogService;
import com.lzb.component.operation.strategy.OperateLogStorage;
import com.lzb.component.operation.strategy.OperateLogUidService;
import com.lzb.component.operation.strategy.impl.OperateLogStorageByMybatisPlus;
import com.lzb.component.operation.strategy.impl.OperationLogMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class OperateLogAutoConfiguration {

	@Bean
	public OperateLogAnnotationInterceptor operateLogAnnotationInterceptor(OperateLogStorage operateLogStorage,
			@Autowired(required = false) OperateLogUidService operateLogUidService) {
		return new OperateLogAnnotationInterceptor(operateLogStorage, operateLogUidService);
	}

	@Bean
	public Advisor operateLogAnnotationAdvisor(OperateLogAnnotationInterceptor advisor) {
		return new OperateLogAnnotationAdvisor(advisor, Sets.newHashSet(OperateLog.class, OperateLogs.class));
	}

	@Bean
	// @ConditionalOnMissingBean(OperateLogStorage.class)
	public OperateLogStorage operateLogStorageByMybatisPlus(OperationLogMapper operationLogMapper) {
		return new OperateLogStorageByMybatisPlus(operationLogMapper);
	}

	@Bean
	public OperationLogService operationLogService(OperateLogStorage operateLogStorage) {
		return new OperationLogService(operateLogStorage);
	}
	
}
