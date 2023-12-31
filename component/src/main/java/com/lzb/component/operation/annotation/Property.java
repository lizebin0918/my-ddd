package com.lzb.component.operation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

	/**
	 * 类/字段的别名 不填则默认类/字段名
	 * @return
	 */
	String value() default "";
	
}
