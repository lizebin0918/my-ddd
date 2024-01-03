package com.lzb.domain.order.aggregation;

import java.math.BigDecimal;

import com.lzb.component.domain.aggregate.BaseEntity;
import com.lzb.domain.order.aggregation.valobj.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单明细<br/>
 * Created on : 2023-08-31 12:51
 * @author lizebin
 */
@Slf4j
@Getter
@Jacksonized
@SuperBuilder
@Setter(AccessLevel.PACKAGE)
public class OrderDetail extends BaseEntity<OrderDetail> {

    private int skuId;

    private OrderStatus orderStatus;

    private BigDecimal price;

    /**
     * null:未知
     * true:锁定库存
     * false:缺货
     */
    private Boolean locked;

    /**
     * 订单明细取消
     */
    void cancel() {
        orderStatus = orderStatus.toCancel();
    }

    /**
     * 更新库存锁定状态
     * @param locked
     */
    void updateLocked(Boolean locked) {
        this.locked = locked;
    }
}
