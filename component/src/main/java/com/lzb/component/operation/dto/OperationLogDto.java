package com.lzb.component.operation.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lzb.component.operation.context.OperationLogContext;
import lombok.Data;

@Data
public class OperationLogDto {

	/**
	 * 用户名
	 */
	private Long uid;

	/**
	 * 业务id
	 */
	private Long bizId;

	/**
	 * 操作表名
	 */
	@TableField("log_table_name")
	private String tableName;

	/**
	 * 业务类型
	 * example 质检  质检结果提交 发货
	 */
	private String bizType;

	/**
	 * 旧状态
	 */
	private String oldStatus;

	/**
	 * 新状态
	 */
	private String newStatus;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 来源
	 */
	private String source;
	
	private String diff;

	private Integer diffId;

	public OperationLogDto(Builder builder) {
		this.uid = builder.uid;
		this.tableName = builder.tableName;
		this.bizType = builder.bizType;
		this.bizId = builder.bizId;
		this.oldStatus = builder.oldStatus;
		this.newStatus = builder.newStatus;
		this.remark = builder.remake;
		this.source = builder.source;
		this.diff = builder.diff;
		this.diffId = builder.diffId;
	}

	public OperationLogDto() {
	}

	public static Builder builder() {
		return new Builder();
	}
	
	
	public static final class Builder {
		/**
		 * 用户名
		 */
		private Long uid;

		/**
		 * 业务id
		 */
		private Long bizId;

		/**
		 * 操作表名
		 */
		@TableField("log_table_name")
		private String tableName;

		/**
		 * 业务类型
		 * example 质检  质检结果提交 发货
		 */
		private String bizType;

		/**
		 * 旧状态
		 */
		private String oldStatus;

		/**
		 * 新状态
		 */
		private String newStatus;

		/**
		 * 备注
		 */
		private String remake;

		/**
		 * 来源
		 */
		private String source;

		/**
		 * diff
		 */
		private String diff;

		private Integer diffId;


		public Builder uid(Long uid) {
			this.uid = uid;
			return this;
		}

		public Builder bizId(Long bizId) {
			this.bizId = bizId;
			return this;
		}

		public Builder tableName(String tableName) {
			this.tableName = tableName;
			return this;
		}

		public Builder bizType(String bizType) {
			this.bizType = bizType;
			return this;
		}

		public Builder oldStatus(String oldStatus) {
			this.oldStatus = oldStatus;
			return this;
		}

		public Builder newStatus(String newStatus) {
			this.newStatus = newStatus;
			return this;
		}

		public Builder setRemake(String remake) {
			this.remake = remake;
			return this;
		}

		public Builder source(String source) {
			this.source = source;
			return this;
		}

		public Builder diffId(Integer diffId) {
			this.diffId = diffId;
			return this;
		}

		public Builder diffDTOS(String diff) {
			this.diff = diff;
			return this;
		}

		public Builder diff(Object oldObject, Object newObject) {
			this.diff = OperationLogContext.diff(oldObject, newObject, false, this.diffId);
			return this;
		}
		

		public OperationLogDto builder() {
			return new OperationLogDto(this);
		}
		
	}

}
