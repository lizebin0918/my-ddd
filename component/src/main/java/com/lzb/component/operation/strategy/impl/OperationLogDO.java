package com.lzb.component.operation.strategy.impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.component.operation.dto.OperationLogDTO;
import lombok.Data;

@TableName("operation_log")
@Data
public class OperationLogDO extends OperationLogDTO {

	@TableId(type = IdType.AUTO)
	private Long id;
	
}
