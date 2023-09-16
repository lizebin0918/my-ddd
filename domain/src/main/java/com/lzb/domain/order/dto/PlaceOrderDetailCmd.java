package com.lzb.domain.order.dto;

import java.math.BigDecimal;

/**
 * 生单指令<br/>
 * Created on : 2023-09-06 13:00
 * @author lizebin
 */
public record PlaceOrderDetailCmd(Integer skuId, BigDecimal price) {


}
