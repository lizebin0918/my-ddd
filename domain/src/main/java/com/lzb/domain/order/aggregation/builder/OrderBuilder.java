package com.lzb.domain.order.aggregation.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lzb.component.domain.aggregate.BaseBuilder;
import com.lzb.component.helper.SpringHelper;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.aggregation.OrderAddress;
import com.lzb.domain.order.aggregation.OrderDetail;
import com.lzb.domain.order.aggregation.OrderDetails;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.domain.order.service.SkuValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * OrderBuilder用于app构建聚合根
 * Created on : 2023-09-07 22:52
 * @author mac
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class OrderBuilder extends BaseBuilder<Order> {

    private final SkuValidator skuValidator;

    private final IdGenerator idGenerator;

    ///////////////////////////////////////////////////////////////////////////
    // 属性值
    ///////////////////////////////////////////////////////////////////////////

    private String currency;
    private BigDecimal exchangeRate;
    private BigDecimal totalShouldPay;
    private BigDecimal totalActualPay;
    private int version;
    private OrderStatus orderStatus;
    private final List<OrderDetail> orderDetails = new ArrayList<>();

    // 聚合根内部值对象
    private OrderAddress orderAddress;

    public static OrderBuilder newInstance() {
        return SpringHelper.getBean(OrderBuilder.class);
    }

    public OrderBuilder currency(@NonNull String currency) {
        this.currency = currency;
        return this;
    }

    public OrderBuilder exchangeRate(@NonNull BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public OrderBuilder totalShouldPay(@NonNull BigDecimal totalShouldPay) {
        this.totalShouldPay = totalShouldPay;
        return this;
    }

    public OrderBuilder totalActualPay(@NonNull BigDecimal totalActualPay) {
        this.totalActualPay = totalActualPay;
        return this;
    }

    public OrderBuilder version(int version) {
        this.version = version;
        return this;
    }

    public OrderBuilder orderStatus(@NonNull OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderBuilder addOrderDetail(@NonNull OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
        return this;
    }

    public OrderBuilder orderAddress(@NonNull OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
        return this;
    }


    @Override
    protected Order doBuild() {
        return new Order(idGenerator.id(), version, orderStatus, currency, exchangeRate, totalShouldPay, totalActualPay, orderAddress, new OrderDetails(orderDetails));
    }

    @Override
    protected void validate(Order entity) {
        skuValidator.assertAllOfSkuIsOnSale(entity.getOrderDetails().getSkuIds());
    }

}
