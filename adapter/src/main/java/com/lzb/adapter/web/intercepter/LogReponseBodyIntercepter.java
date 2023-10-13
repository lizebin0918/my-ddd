package com.lzb.adapter.web.intercepter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * <br/>
 * Created on : 2023-10-13 18:12
 *
 * @author lizebin
 */
@Slf4j
@Component
public class LogReponseBodyIntercepter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);
        try {
            // Execution request chain
            filterChain.doFilter(request, resp);
            byte[] responseBody = resp.getContentAsByteArray();
            log.info("response-body = {}", new String(responseBody, StandardCharsets.UTF_8));
        } finally {
            // Finally remember to respond to the client with the cached data.
            resp.copyBodyToResponse();
        }
    }

}
