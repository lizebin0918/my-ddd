package com.lzb.component.operation.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.lzb.component.operation.annotation.OperateLog;
import com.lzb.component.operation.annotation.OperateLogs;
import com.lzb.component.operation.context.OperationLogContext;
import com.lzb.component.operation.dto.OperationLogDTO;
import com.lzb.component.operation.strategy.OperateLogStorage;
import com.lzb.component.operation.strategy.OperateLogUidService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Slf4j
@AllArgsConstructor
public class OperateLogAnnotationInterceptor implements MethodInterceptor {

	private final SpelExpressionParser parser = new SpelExpressionParser();

	private final DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

	private final OperateLogStorage operateLogStorage;

	private final OperateLogUidService operateLogUidService;

	@Nullable
	@Override
	public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {

		Method method = invocation.getMethod();
		List<OperationLogDTO> operationLogDTOS = new ArrayList<>();
		List<OperateLog> operateLogList = new ArrayList<>();
		if(method.isAnnotationPresent(OperateLog.class)){
			OperateLog[] operateLogArr = method.getAnnotationsByType(OperateLog.class);
			operateLogList.addAll(Lists.newArrayList(operateLogArr));
		}
		if(method.isAnnotationPresent(OperateLogs.class)){
			OperateLogs[] operateLogsArr = method.getAnnotationsByType(OperateLogs.class);
			for (OperateLogs annotation : operateLogsArr) {
				operateLogList.addAll(Lists.newArrayList(annotation.value()));
			}
		}
		// 原始方法执行
		// todo 方法执行结果是否需要保存
		Object proceed = proceed(invocation);
		try {
			for (OperateLog annotation : operateLogList) {
				OperationLogDTO operationLogDTO;
				try {
					operationLogDTO = builderOperationLogDTO(annotation, invocation);
				} catch (Exception e){
					continue;
				}
				operationLogDTOS.add(operationLogDTO);
			}
			operateLogStorage.saveOperateLog(operationLogDTOS);
		}
		catch (Exception e) {
			// 不抛出异常 不影响正常的log 记录
			log.error("operate log error", e);
		} finally {
			OperationLogContext.clearDiff();
			OperationLogContext.clearDiffId();
			OperationLogContext.clearContext();
		}
		return proceed;
	}


	public Object proceed(MethodInvocation methodInvocation) throws Throwable {
		return methodInvocation.proceed();
	}

	public OperationLogDTO builderOperationLogDTO(OperateLog annotation, MethodInvocation methodInvocation) {

		Method method = methodInvocation.getMethod();
		Object[] arguments = methodInvocation.getArguments();
		String[] params = discoverer.getParameterNames(method);
		StandardEvaluationContext context = OperationLogContext.getContext();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				context.setVariable(params[i], arguments[i]);
			}
		}
		String bizIdEL = annotation.bizId();
		String uidEL = annotation.uid();
		String tableNameEL = annotation.tableName();
		String bizTypeEL = annotation.bizType();
		String oldStatusEL = annotation.oldStatus();
		String newStatusEL = annotation.newStatus();
		String remarkEL = annotation.remark();
		String sourceEL = annotation.source();
		String diffIdEl = annotation.diffId();
		OperationLogDTO operationLogDTO = new OperationLogDTO();

		if (Objects.nonNull(operateLogUidService)) {
			operationLogDTO.setUid(operateLogUidService.getOperateLogUID());
		}
		else {
			initValue(OperationLogDTO::setUid, operationLogDTO, uidEL, Long.class, context);
		}
		initValue(OperationLogDTO::setBizId, operationLogDTO, bizIdEL, Long.class, context);
		initValue(OperationLogDTO::setTableName, operationLogDTO, tableNameEL, String.class, context);
		initValue(OperationLogDTO::setBizType, operationLogDTO, bizTypeEL, String.class, context);
		initValue(OperationLogDTO::setOldStatus, operationLogDTO, oldStatusEL, String.class, context);
		initValue(OperationLogDTO::setNewStatus, operationLogDTO, newStatusEL, String.class, context);
		initValue(OperationLogDTO::setRemark, operationLogDTO, remarkEL, String.class, context);
		initValue(OperationLogDTO::setSource, operationLogDTO, sourceEL, String.class, context);
		if(StringUtils.isNotEmpty(diffIdEl)){
			initValue(OperationLogDTO::setDiffId, operationLogDTO, diffIdEl, Integer.class, context);
			operationLogDTO.setDiff(OperationLogContext.getDiffDTOList(operationLogDTO.getDiffId()));
		}else{
			operationLogDTO.setDiff(OperationLogContext.getDiffDTOList());
		}
		return operationLogDTO;
	}

	public <T, V> void initValue(BiConsumer<T, V> biConsumer, T t, String el, Class<V> clazz, StandardEvaluationContext context) {
		V v = null;
		if (StringUtils.isNotBlank(el)) {
			Expression bizIdExpression = parser.parseExpression(el);
			v = bizIdExpression.getValue(context, clazz);
		}
		biConsumer.accept(t, v);
	}

}
