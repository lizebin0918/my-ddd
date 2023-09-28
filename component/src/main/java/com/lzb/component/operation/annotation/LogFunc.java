package com.lzb.component.operation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface LogFunc {

	/**
	 * 自定义函数的别名，如果为空即使用函数名
	 */
	String value() default "";
	
}
