package com.lzb.app.cmd.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 生单指令<br/>
 * Created on : 2023-09-06 13:00
 * @author lizebin
 */
@Getter
@Builder
@AllArgsConstructor
public class PlaceOrderCmd {

    private String currency;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

    ///////////////////////////////////////////////////////////////////////////
    // 地址相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phoneNumber;

    private String firstName;

    private String lastName;

    /**
     * 地址1
     */
    private String addressLine1;

    /**
     * 地址2
     */
    private String addressLine2;

    /**
     * 国家
     */
    private String country;

}
