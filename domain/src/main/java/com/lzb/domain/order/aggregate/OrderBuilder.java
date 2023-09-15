package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lzb.component.helper.SpringHelper;
import com.lzb.domain.common.aggregate.BaseBuilder;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.domain.order.service.SkuValidator;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
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
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderBuilder extends BaseBuilder<Order> {

    private final SkuValidator skuValidator;

    ///////////////////////////////////////////////////////////////////////////
    // 属性值
    ///////////////////////////////////////////////////////////////////////////

    private long orderId;
    private String currency;
    private BigDecimal exchangeRate;
    private BigDecimal totalShouldPay;
    private BigDecimal totalActualPay;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private int version;
    private OrderStatus orderStatus;
    private final List<OrderDetail> innerOrderDetails = new ArrayList<>();

    // 聚合根内部值对象
    private FullName fullName;
    private FullAddressLine fullAddressLine;
    private OrderAddress orderAddress;
    private OrderDetails orderDetails;

    public static OrderBuilder newInstance() {
        return SpringHelper.getBean(OrderBuilder.class);
    }

    public OrderBuilder orderId(long orderId) {
        this.orderId = orderId;
        return this;
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

    public OrderBuilder email(@NonNull String email) {
        this.email = email;
        return this;
    }

    public OrderBuilder phoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public OrderBuilder firstName(@NonNull String firstName) {
        this.firstName = firstName;
        return this;
    }

    public OrderBuilder lastName(@NonNull String lastName) {
        this.lastName = lastName;
        return this;
    }

    public OrderBuilder addressLine1(@NonNull String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public OrderBuilder addressLine2(@NonNull String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public OrderBuilder country(@NonNull String country) {
        this.country = country;
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
        innerOrderDetails.add(orderDetail);
        return this;
    }


    @Override
    protected Order doBuild() {
        fullName = new FullName(firstName, lastName);
        fullAddressLine = new FullAddressLine(addressLine1, addressLine2);
        orderAddress = new OrderAddress(orderId, fullName, fullAddressLine, email, phoneNumber, country);
        orderDetails = new OrderDetails(this.innerOrderDetails);
        return new Order(orderId, version, orderStatus, currency, exchangeRate, totalShouldPay, totalActualPay, orderAddress, orderDetails);
    }

    @Override
    protected void validate(Order order) {
        skuValidator.assertAllOfSkuIsOnSale(order.getOrderDetails().getSkuIds());
    }

}
