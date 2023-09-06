package com.lzb.component.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * <br/>
 * Created on : 2023-09-06 13:02
 * @author lizebin
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MyReponse<R> {

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

    public static <T> MyReponse<T> success(T data) {
        return MyReponse.<T>builder().code(SUCCESS_CODE).data(data).build();
    }

    /**
     * 是否成功
     * @return
     */
    public boolean isSuccess() {
        return Objects.equals(SUCCESS_CODE, this.code);
    }

}
