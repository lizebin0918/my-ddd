package com.lzb.domain.order.gateway;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

/**
 * <br/>
 * Created on : 2023-09-26 10:09
 * @author lizebin
 */
@Validated
public interface OrderGateway {

    Set<Long> queryByEmail(@NotBlank(message = "邮箱不能为空") String email);

}
