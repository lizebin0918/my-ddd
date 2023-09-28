package com.lzb.component.operation.strategy.impl;

import com.lzb.component.operation.dto.OperationLogDTO;
import com.lzb.component.operation.strategy.AbstractOperateLogStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Slf4j
public class OperateLogStorageByMybatisPlus extends AbstractOperateLogStorage {

	@Autowired
	OperationLogMapper operationLogMapper;

	@Override
	public void saveOperationLog(OperationLogDTO dto) {
		OperationLogDO operationLogDO = new OperationLogDO();
		operationLogDO.setUid(dto.getUid());
		operationLogDO.setBizId(dto.getBizId());
		operationLogDO.setTableName(dto.getTableName());
		operationLogDO.setBizType(dto.getBizType());
		operationLogDO.setOldStatus(dto.getOldStatus());
		operationLogDO.setNewStatus(dto.getNewStatus());
		operationLogDO.setRemark(dto.getRemark());
		operationLogDO.setSource(dto.getSource());
		operationLogDO.setDiff(dto.getDiff());
		operationLogMapper.insert(operationLogDO);
	}
}
