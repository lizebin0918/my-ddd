package com.lzb.component.operation.strategy;

import java.util.List;

import com.lzb.component.operation.dto.OperationLogDTO;


public interface OperateLogStorage {

	/**
	 * 注意自我实现需要异常 try catch log 不能影响到正常业务的执行
	 * @param operationLogDTOS
	 */
	void saveOperateLog(List<OperationLogDTO> operationLogDTOS);
	
}
