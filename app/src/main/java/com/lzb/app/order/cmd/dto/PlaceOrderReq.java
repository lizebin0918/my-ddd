package com.lzb.app.order.cmd.dto;

import java.math.BigDecimal;

/**
 * 生单指令<br/>
 * Created on : 2023-09-06 13:00
 * @author lizebin
 */
public record PlaceOrderReq(String currency, BigDecimal exchangeRate, BigDecimal totalShouldPay,
                            BigDecimal totalActualPay, String email, String phoneNumber, String firstName,
                            String lastName, String addressLine1, String addressLine2, String country) {


}
