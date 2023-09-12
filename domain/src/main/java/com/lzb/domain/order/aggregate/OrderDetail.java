package com.lzb.domain.order.aggregate;

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
     * 直接提供给仓储层使用，无需校验业务逻辑
     * @param id
     * @param orderId
     * @param skuId
     * @param orderStatus
     * @param price
     */
    public OrderDetail(long id, long orderId, int skuId, @NonNull OrderStatus orderStatus, @NonNull BigDecimal price) {
        super(id);
        this.orderId = orderId;
        this.skuId = skuId;
        this.orderStatus = orderStatus;
        this.price = price;
    }

    /**
     * 订单明细取消
     */
    void cancel() {
        orderStatus = OrderStatus.CANCELED;
    }

}
