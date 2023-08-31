package com.lzb.domain.order;

import java.math.BigDecimal;

import com.lzb.domain.common.BaseEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单明细<br/>
 * Created on : 2023-08-31 12:51
 * @author lizebin
 */
@Slf4j
@Getter
@SuperBuilder
public class OrderDetail extends BaseEntity<OrderDetail> {

    private Long orderId;

    private Integer skuId;

    private String orderStatus;

    private BigDecimal price;

}
