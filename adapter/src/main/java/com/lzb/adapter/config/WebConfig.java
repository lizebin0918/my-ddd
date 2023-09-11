package com.lzb.adapter.config;

import java.util.List;

import com.lzb.adapter.intercepter.MyResponseBodyHandleReturnValue;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new MyResponseBodyHandleReturnValue());
    }

}