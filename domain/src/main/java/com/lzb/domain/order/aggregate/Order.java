package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.component.exception.BizException;
import com.lzb.domain.common.aggregate.BaseAggregate;
import com.lzb.domain.order.aggregate.builder.OrderAddressBuilder;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.domain.order.event.OrderCanceledEvent;
import com.lzb.domain.order.event.OrderPlacedEvent;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import lombok.Getter;
import lombok.NonNull;
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
public class Order extends BaseAggregate<Order> {

    @NonNull
    private final OrderStatus orderStatus;

    @NonNull
    private final String currency;

    @NonNull
    private final BigDecimal exchangeRate;

    @NonNull
    private final BigDecimal totalShouldPay;

    @NonNull
    private final BigDecimal totalActualPay;

    @NonNull
    private final OrderAddress orderAddress;

    @NonNull
    private final OrderDetails orderDetails;


    /**
     * 仓储层使用，无需校验业务逻辑，直接构造
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
    public Order(long id,
            int version,
            @NonNull OrderStatus orderStatus,
            @NonNull String currency,
            @NonNull BigDecimal exchangeRate,
            @NonNull BigDecimal totalShouldPay,
            @NonNull BigDecimal totalActualPay,
            @NonNull OrderAddress orderAddress,
            @NonNull List<OrderDetail> orderDetails) {
        super(id);
        this.version = version;
        this.orderStatus = orderStatus;
        this.currency = currency;
        this.exchangeRate = exchangeRate;
        this.totalShouldPay = totalShouldPay;
        this.totalActualPay = totalActualPay;
        this.orderAddress = orderAddress;
        this.orderDetails = new OrderDetails(orderDetails);
    }


    public void updateAddress(String email,
            String phoneNumber, FullName fullName,
            FullAddressLine fullAddressLine, String country) {
        if (isShipped()) {
            throw new BizException("订单已发货，不能修改地址");
        }
        OrderAddress newOrderAddress = OrderAddressBuilder.newInstance(this.orderAddress)
                .addressLine1(fullAddressLine.getAddressLine1())
                .addressLine2(fullAddressLine.getAddressLine2())
                .country(country)
                .email(email)
                .firstName(fullName.getFirstName())
                .lastName(fullName.getLastName())
                .phoneNumber(phoneNumber).build();
        this.orderAddress = newOrderAddress;
    }

    /**
     * 更新库存结果
     * @param lockStock
     */
    public void updateLockStock(LockStockDto lockStock) {
        Map<Integer, List<OrderDetail>> skuId2OrderDetails = StreamEx.of(orderDetails.toStream()).groupingBy(OrderDetail::getSkuId);
        skuId2OrderDetails.keySet().forEach(skuId -> {
            List<OrderDetail> skuOrderDetails = skuId2OrderDetails.getOrDefault(skuId, Collections.emptyList());
            for (int i = 1; i <= skuOrderDetails.size(); i++) {
                skuOrderDetails.get(i - 1).updateLocked(i <= lockStock.getDetail(skuId).getLockedNum());
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
        orderStatus = OrderStatus.CANCELED;
        orderDetails.forEach(OrderDetail::cancel);
        addEvent(OrderCanceledEvent.create(id));
    }

    /**
     * 是否取消
     * @return
     */
    public boolean isCancel() {
        return OrderStatus.CANCELED == orderStatus;
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
}
