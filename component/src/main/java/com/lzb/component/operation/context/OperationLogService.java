package com.lzb.component.operation.context;

import com.google.common.collect.Lists;
import com.lzb.component.operation.dto.OperationLogDTO;
import com.lzb.component.operation.strategy.OperateLogStorage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OperationLogService {

	private OperateLogStorage operateLogStorage;

	public void save(OperationLogDTO operationLogDTO) {
		operateLogStorage.saveOperateLog(Lists.newArrayList(operationLogDTO));
	}
	
}
