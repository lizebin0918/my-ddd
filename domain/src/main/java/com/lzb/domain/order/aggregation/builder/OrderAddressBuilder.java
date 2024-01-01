package com.lzb.domain.order.aggregation.builder;

import com.lzb.component.domain.aggregate.BaseBuilder;
import com.lzb.component.helper.SpringHelper;
import com.lzb.domain.order.aggregation.OrderAddress;
import com.lzb.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.domain.order.aggregation.valobj.FullName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-15 22:08
 * @author lizebin
 */
@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class OrderAddressBuilder extends BaseBuilder<OrderAddress> {

    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String country;

    private FullName fullName;

    private FullAddressLine fullAddressLine;


    public OrderAddressBuilder id(long id) {
        this.id = id;
        return this;
    }

    public OrderAddressBuilder email(String email) {
        this.email = email;
        return this;
    }

    public OrderAddressBuilder phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public OrderAddressBuilder country(String country) {
        this.country = country;
        return this;
    }

    public static OrderAddressBuilder newInstance() {
        return SpringHelper.getBean(OrderAddressBuilder.class);
    }

    public OrderAddressBuilder fullName(FullName fullName) {
        this.fullName = fullName;
        return this;
    }

    public OrderAddressBuilder fullAddressLine(FullAddressLine fullAddressLine) {
        this.fullAddressLine = fullAddressLine;
        return this;
    }

    @Override
    protected OrderAddress doBuild() {
        return new OrderAddress(id, fullName, fullAddressLine, email, phoneNumber, country);
    }

    @Override
    protected void validate(OrderAddress entity) {
        super.validate(entity);
        log.info("订单地址业务校验");
    }
}
