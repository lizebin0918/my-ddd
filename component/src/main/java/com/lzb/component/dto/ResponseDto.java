package com.lzb.component.dto;

import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;

/**
 * <br/>
 * Created on : 2023-09-06 13:02
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class ResponseDto<R> {

    public static final String SUCCESS_CODE = "SUCCESS";

    /**
     * 响应状态码
     */
    @NonNull
    private final String code;

    /**
     * 响应描述
     */
    private final String msg;

    /**
     * 响应业务数据
     */
    private final R data;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(SUCCESS_CODE, "", data);
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isSuccess() {
        return Objects.equals(SUCCESS_CODE, this.code);
    }

}
