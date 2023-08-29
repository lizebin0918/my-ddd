package com.lzb.component.env;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

import static com.lzb.component.env.Constants.*;

/**
 * 非local环境
 * @author mac
 * @date 2022/09/07
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile({NOT + LOCAL + " & " + NOT + DEV})
public @interface NotLocalAndDev {
}
