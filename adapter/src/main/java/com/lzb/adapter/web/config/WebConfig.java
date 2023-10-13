package com.lzb.adapter.web.config;

import java.util.List;

import com.lzb.adapter.web.intercepter.LogRequestParam;
import com.lzb.adapter.web.intercepter.MyResponseBodyHandleReturnValue;
import jakarta.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private LogRequestParam logRequestParam;

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new MyResponseBodyHandleReturnValue());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logRequestParam).addPathPatterns("/**");
    }

}