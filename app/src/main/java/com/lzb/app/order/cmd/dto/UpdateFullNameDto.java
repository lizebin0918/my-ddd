package com.lzb.app.order.cmd.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 前端只会传姓名，只更新姓名即可<br/>
 * Created on : 2023-09-17 21:34
 * @author mac
 */
public record UpdateFullNameDto(
        long orderId,
        @NotBlank
        String firstName,
        String lastName) {

}
