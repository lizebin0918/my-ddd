package com.lzb.domain.order.aggregation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import com.lzb.component.domain.aggregate.BaseAggregation;
import com.lzb.component.exception.BizException;
import com.lzb.domain.order.aggregation.valobj.FullName;
import com.lzb.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.domain.order.aggregation.valobj.OrderStatus;
import com.lzb.domain.order.dto.SkuStockLockDto;
import com.lzb.domain.order.event.OrderCanceledEvent;
import com.lzb.domain.order.event.OrderPlacedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
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
@Jacksonized
@SuperBuilder
@Setter(AccessLevel.PACKAGE)
public class Order extends BaseAggregation<Order> {

    private OrderStatus orderStatus;

    private String currency;

    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

    private OrderAddress orderAddress;

    private OrderDetails orderDetails;

    public void updateAddress(OrderAddress newOrderAddress) {
        if (isShipped()) {
            throw new BizException("订单已发货，不能修改地址");
        }
        this.orderAddress = newOrderAddress;
    }

    public void updateSkuLockStock(List<SkuStockLockDto> skuStockLockDtos) {
        Map<Integer, SkuStockLockDto> skuId2Lock = skuStockLockDtos.stream().collect(Collectors.toMap(SkuStockLockDto::skuId, Function.identity()));
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
     * 更新姓名
     * @param fullName
     */
    public void updateFullName(FullName fullName) {
        Assert.notBlank(fullName.getFirstName(), "firstName is null");
        this.orderAddress = orderAddress.toBuilder().fullName(fullName).build();
    }

    public boolean canCancel() {
        return false;
    }

    public void addOrderDetail(@NonNull OrderDetail orderDetail) {
        if (Objects.isNull(orderDetails)) {
            this.orderDetails = new OrderDetails(new ArrayList<>());
        }
        orderDetails.add(orderDetail);
    }
}
