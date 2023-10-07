package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.component.domain.aggregate.BaseEntity;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单明细<br/>
 * Created on : 2023-08-31 12:51
 * @author lizebin
 */
@Slf4j
@Getter
// 方便测试构造
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

    protected OrderDetail(long id) {
        super(id);
    }

    /**
     * 直接提供给仓储层使用，无需校验业务逻辑
     * @param id
     * @param orderId
     * @param skuId
     * @param orderStatus
     * @param price
     */
    @JsonCreator
    public OrderDetail(long id, int skuId, @NonNull OrderStatus orderStatus, @NonNull BigDecimal price, Boolean locked) {
        super(id);
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
