package com.lzb.domain.order.aggregate;

import java.beans.ConstructorProperties;
import java.math.BigDecimal;

import com.lzb.domain.common.aggregate.BaseEntity;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单明细<br/>
 * Created on : 2023-08-31 12:51
 * @author lizebin
 */
@Slf4j
@Getter
public class OrderDetail extends BaseEntity<OrderDetail> {

    private long orderId;

    private int skuId;

    @NonNull
    private OrderStatus orderStatus;

    @NonNull
    private BigDecimal price;

    /**
     * null:未知
     * true:锁定库存
     * false:缺货
     */
    private Boolean locked;

    /**
     * 直接提供给仓储层使用，无需校验业务逻辑
     * @param id
     * @param orderId
     * @param skuId
     * @param orderStatus
     * @param price
     */
    @ConstructorProperties({"id", "orderId", "skuId", "orderStatus", "price", "locked"})
    public OrderDetail(long id, long orderId, int skuId, @NonNull OrderStatus orderStatus, @NonNull BigDecimal price, Boolean locked) {
        super(id);
        this.orderId = orderId;
        this.skuId = skuId;
        this.orderStatus = orderStatus;
        this.price = price;
        this.locked = locked;
    }

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
