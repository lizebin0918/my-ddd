package com.lzb.adapter.intercepter;

import java.util.Objects;

import com.lzb.adapter.annotation.MyResponseBody;
import com.lzb.component.dto.ResponseDto;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MyResponseBodyHandleReturnValue implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {
    /**
     * 处理所有非异常的错误
     *
     * @param returnType
     * @return
     */
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        //如果已经是基础的返回值
        return Objects.nonNull(returnType.getAnnotatedElement().getAnnotation(MyResponseBody.class));
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse httpResponse = webRequest.getNativeResponse(HttpServletResponse.class);
        httpResponse.setContentType("application/json;charset=utf-8");
        ResponseDto<?> response = new ResponseDto<>(ResponseDto.SUCCESS_CODE, null, returnValue);
        httpResponse.getWriter().write(JsonUtils.SPRING_MVC_RETURN_OBJECT_MAPPER.writeValueAsString(response));

    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return supportsReturnType(returnType);
    }
}