package com.lzb.component.operation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OperateLogs.class)
public @interface OperateLog {


	/**
	 * 业务ID
	 * 必填
	 * SpEL表达式
	 */
	String bizId();

	/**
	 * 业务类型
	 * 必填
	 * SpEL表达式
	 */
	String bizType();

	/**
	 * 操作人ID
	 * 可选
	 * SpEL表达式
	 */
	String uid() default "";

	/**
	 * 操作表名
	 * @return
	 */
	String tableName() default "";

	/**
	 * 旧状态
	 * @return
	 */
	String oldStatus() default "";

	/**
	 * 新状态
	 */
	String newStatus() default "";

	/**
	 * 备注
	 */
	String remark() default "";

	/**
	 * 调用来源
	 * @return
	 */
	String source() default "";

	/**
	 * 多个注解的时候用这个指定注解对应的diff
	 * @return
	 */
	String diffId() default "";
	
	
	
	
	
	
}
