package com.lzb.adapter.web.intercepter;

import java.util.Map;

import com.lzb.component.utils.json.JsonUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Slf4j
@Component
public class LogRequestParamIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        log.info("reqeust-url {}", request.getRequestURL());
        log.info("request-param {}", JsonUtils.toJSONString(request.getParameterMap()));
        return true;
    }
}