package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;

import com.lzb.component.helper.SpringHelper;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-09 13:57
 * @author lizebin
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderDetailBuilder {

    private final IdGenerator idGenerator;

    ///////////////////////////////////////////////////////////////////////////
    // 属性值
    ///////////////////////////////////////////////////////////////////////////

    private long orderId;
    private int skuId;
    private BigDecimal price;
    private OrderStatus orderStatus;

    public static OrderDetailBuilder newInstance() {
        return SpringHelper.getBean(OrderDetailBuilder.class);
    }

    public OrderDetailBuilder orderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderDetailBuilder skuId(int skuId) {
        this.skuId = skuId;
        return this;
    }

    public OrderDetailBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public OrderDetailBuilder orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderDetail build() {
        return new OrderDetail(idGenerator.id(), orderId, skuId, orderStatus, price);
    }

}
