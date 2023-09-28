package com.lzb.component.operation.strategy;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.lzb.component.operation.dto.OperationLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;


@Slf4j
public abstract class AbstractOperateLogStorage implements OperateLogStorage {

	private static final ThreadFactory THREAD_FACTORY = new CustomizableThreadFactory("operation-log-");

	private final ExecutorService executorService = new ThreadPoolExecutor(4, 5, 60
			, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), THREAD_FACTORY, new ThreadPoolExecutor.CallerRunsPolicy());

	@Override
	public void saveOperateLog(List<OperationLogDTO> operationLogDTOS) {
		if(CollectionUtils.isEmpty(operationLogDTOS))return;
		// 过滤bizId为空的数据，
		operationLogDTOS.stream().filter(operationLogDTO -> Objects.nonNull(operationLogDTO.getBizId())).forEach(operationLogDTO -> {
			executorService.execute(() -> {
				try {
					saveOperationLog(operationLogDTO);
				}
				catch (Exception e) {
					// 吃掉异常以免log 影响正常的业务流程
					log.error("log save error", e);
				}
			});
		});
	}
	// todo 暂时不批量插入
	public abstract void saveOperationLog(OperationLogDTO operationLogDTO);



}
