package com.lzb.app.order.cmd.dto;

import java.math.BigDecimal;

/**
 * 生单指令<br/>
 * Created on : 2023-09-06 13:00
 * @author lizebin
 */
public record PlaceOrderDetailDto(Integer skuId, BigDecimal price) {


}
