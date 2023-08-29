package com.lzb.component.env;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

import static com.lzb.component.env.Constants.*;

/**
 * 用于CI跑测试的环境，连接测试环境的空数据库
 * @author mac
 * @date 2022/7/13
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile(DEV)
public @interface Dev {
}
