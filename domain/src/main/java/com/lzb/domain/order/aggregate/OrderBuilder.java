package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.component.helper.SpringHelper;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import lombok.RequiredArgsConstructor;
import org.mockito.internal.matchers.Or;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created on : 2023-09-07 22:52
 * @author mac
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderBuilder {

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
    private List<OrderDetailBuilder> orderDetailBuilders;

    public static OrderBuilder newInstance() {
        return SpringHelper.getBean(OrderBuilder.class);
    }

    public OrderBuilder orderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder currency(String currency) {
        this.currency = currency;
        return this;
    }

    public OrderBuilder exchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public OrderBuilder totalShouldPay(BigDecimal totalShouldPay) {
        this.totalShouldPay = totalShouldPay;
        return this;
    }

    public OrderBuilder totalActualPay(BigDecimal totalActualPay) {
        this.totalActualPay = totalActualPay;
        return this;
    }

    public OrderBuilder email(String email) {
        this.email = email;
        return this;
    }

    public OrderBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public OrderBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public OrderBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public OrderBuilder addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public OrderBuilder addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public OrderBuilder country(String country) {
        this.country = country;
        return this;
    }

    public OrderBuilder version(int version) {
        this.version = version;
        return this;
    }

    public OrderBuilder orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderBuilder addDetailBuilder(OrderDetailBuilder orderDetailBuilder) {
        orderDetailBuilders.add(orderDetailBuilder);
        return this;
    }


    public Order build() {
        var fullName = new FullName(firstName, lastName);
        var fullAddressLine = new FullAddressLine(addressLine1, addressLine2);
        var orderAddress = new OrderAddress(orderId, fullName, fullAddressLine, email, phoneNumber, country);
        var orderDetails = new OrderDetails(orderDetailBuilders.stream().map(OrderDetailBuilder::build).toList());
        return new Order(orderId, version, orderStatus, currency,
                exchangeRate, totalShouldPay, totalActualPay, orderAddress, orderDetails);
    }

}
