package com.lzb.domain.order.aggregation;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.component.domain.aggregate.BaseAggregate;
import com.lzb.component.exception.BizException;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.domain.order.event.OrderCanceledEvent;
import com.lzb.domain.order.event.OrderPlacedEvent;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.domain.order.dto.SkuStockLock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;

/**
 * 订单聚合根<br/>
 * 实体所有写的操作，必须通过聚合根调用，所以改成包可见(default)
 * 实体所有读操作，可以改成公共(public)
 * Created on : 2022-06-11 14:41
 *
 * @author lizebin
 */
@Slf4j
@Getter
// 方便测试构造
@Setter(AccessLevel.PACKAGE)
public class Order extends BaseAggregate<Order> {

    private OrderStatus orderStatus;

    private String currency;

    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

    private OrderAddress orderAddress;

    private OrderDetails orderDetails;

    protected Order(long id) {
        super(id);
    }

    /**
     * 构造器，@ConstructorProperties 用于反序列化
     * @param id
     * @param version
     * @param orderStatus
     * @param currency
     * @param exchangeRate
     * @param totalShouldPay
     * @param totalActualPay
     * @param orderAddress
     * @param orderDetails
     */
    @JsonCreator
    public Order(long id,
            int version,
            @NonNull OrderStatus orderStatus,
            @NonNull String currency,
            @NonNull BigDecimal exchangeRate,
            @NonNull BigDecimal totalShouldPay,
            @NonNull BigDecimal totalActualPay,
            @NonNull OrderAddress orderAddress,
            @NonNull OrderDetails orderDetails) {
        super(id);
        this.version = version;
        this.orderStatus = orderStatus;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.totalShouldPay = totalShouldPay;
        this.totalActualPay = totalActualPay;
        this.orderAddress = orderAddress;
        this.orderDetails = orderDetails;
    }

    public void updateAddress(OrderAddress newOrderAddress) {
        if (isShipped()) {
            throw new BizException("订单已发货，不能修改地址");
        }
        this.orderAddress = newOrderAddress;
    }

    public void updateSkuLockStock(List<SkuStockLock> skuStockLocks) {
        Map<Integer, SkuStockLock> skuId2Lock = skuStockLocks.stream().collect(Collectors.toMap(SkuStockLock::skuId, Function.identity()));
        Map<Integer, List<OrderDetail>> skuId2OrderDetails = StreamEx.of(orderDetails.toStream()).groupingBy(OrderDetail::getSkuId);
        skuId2OrderDetails.keySet().forEach(skuId -> {
            List<OrderDetail> skuOrderDetails = skuId2OrderDetails.getOrDefault(skuId, Collections.emptyList());
            for (int i = 1; i <= skuOrderDetails.size(); i++) {
                skuOrderDetails.get(i - 1).updateLocked(i <= skuId2Lock.get(skuId).lockedNum());
            }
        });
    }

    public void updateTotalActualPay(BigDecimal totalActualPay) {
        this.totalActualPay = totalActualPay;
    }

    /**
     * 取消订单
     */
    public void cancel() {
        orderStatus = orderStatus.toCancel();
        orderDetails.forEach(OrderDetail::cancel);
        addEvent(OrderCanceledEvent.create(id));
    }

    /**
     * 是否取消
     * @return
     */
    public boolean isCancel() {
        return orderStatus.isCancel();
    }

    /**
     * 生单
     */
    public void place() {
        addEvent(OrderPlacedEvent.create(this.id));
    }

    public boolean isShipped() {
        return false;
    }

    /**
     * 只更新姓名
     * @param fullName
     */
    public void updateFullName(FullName fullName) {
        this.orderAddress.updateFullName(fullName);
    }

    public boolean canCancel() {
        return false;
    }
}
