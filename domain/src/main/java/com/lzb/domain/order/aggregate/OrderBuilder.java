package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;

import com.lzb.component.helper.SpringHelper;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;

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

    private final ProductGateway productGateway;

    private final IdGenerator idGenerator;

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
    public static OrderBuilder newInstance() {
        return SpringHelper.getBean(OrderBuilder.class);
    }

    public long id() {
        return idGenerator.id();
    }

}
