package com.lzb.component.dto;

import java.util.Objects;

import com.lzb.component.arch.ArchUnitIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.junit.Ignore;

/**
 * <br/>
 * Created on : 2023-09-06 13:02
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class ResponseDto<R> {

    private ResponseDto() {

    }

    public static final String SUCCESS_CODE = "SUCCESS";

    /**
     * 响应状态码
     */
    @NonNull
    private String code;

    /**
     * 响应描述
     */
    private String msg;

    /**
     * 响应业务数据
     */
    private R data;

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
