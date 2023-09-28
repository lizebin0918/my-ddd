package com.lzb.component.operation.strategy;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;

import com.lzb.component.operation.dto.OperationLogDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;


@Slf4j
public abstract class AbstractOperateLogStorage implements OperateLogStorage {

	private static final ThreadFactory THREAD_FACTORY = new CustomizableThreadFactory("operation-log-");

	@Override
	public void saveOperateLog(List<OperationLogDto> operationLogDtos) {
		if(CollectionUtils.isEmpty(operationLogDtos))return;
		// 过滤bizId为空的数据，
		operationLogDtos.stream().filter(operationLogDTO -> Objects.nonNull(operationLogDTO.getBizId())).forEach(operationLogDTO -> {
				try {
					saveOperationLog(operationLogDTO);
				}
				catch (Exception e) {
					// 吃掉异常以免log 影响正常的业务流程
					log.error("log save error", e);
				}
		});
	}
	// todo 暂时不批量插入
	public abstract void saveOperationLog(OperationLogDto operationLogDTO);



}
