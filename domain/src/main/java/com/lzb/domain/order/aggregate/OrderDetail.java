package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;

import com.lzb.domain.common.BaseEntity;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.Getter;
import lombok.NonNull;
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

    @NonNull
    private Long orderId;

    @NonNull
    private Integer skuId;

    @NonNull
    private OrderStatus orderStatus;

    @NonNull
    private BigDecimal price;

    /**
     * 订单明细取消
     */
    void cancel() {
        orderStatus = OrderStatus.CANCELED;
    }
}
