package com.lzb.component.operation.diff;

import lombok.Data;

@Data
public class DiffFiledDTO {

	/**
	 * 字段名
	 */
	private String fieldName;

	/**
	 * 旧字段别名
	 */
	private String oldFieldAlias;

	/**
	 * 新字段别名
	 */
	private String newFieldAlias;

	/**
	 * 旧值
	 */
	private Object oldValue;

	/**
	 * 新值
	 */
	private Object newValue;
	
}
