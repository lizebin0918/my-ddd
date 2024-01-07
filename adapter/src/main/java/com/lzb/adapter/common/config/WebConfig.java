package com.lzb.adapter.common.config;

import java.util.List;

import com.lzb.adapter.common.intercepter.LogRequestParamIntercepter;
import com.lzb.adapter.common.intercepter.MyResponseBodyHandleReturnValue;
import jakarta.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LogRequestParamIntercepter logRequestParamIntercepter;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new MyResponseBodyHandleReturnValue());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logRequestParamIntercepter).addPathPatterns("/**");
    }

}