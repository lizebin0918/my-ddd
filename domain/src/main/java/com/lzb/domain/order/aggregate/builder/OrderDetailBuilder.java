package com.lzb.domain.order.aggregate.builder;

import java.math.BigDecimal;

import com.lzb.component.helper.SpringHelper;
import com.lzb.component.id.IdGenerator;
import com.lzb.component.domain.aggregate.BaseBuilder;
import com.lzb.domain.order.aggregate.OrderDetail;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-09 13:57
 * @author lizebin
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class OrderDetailBuilder extends BaseBuilder<OrderDetail> {

    private final IdGenerator idGenerator;


    ///////////////////////////////////////////////////////////////////////////
    // 属性值
    ///////////////////////////////////////////////////////////////////////////

    private long id;
    private int skuId;
    private BigDecimal price;
    private OrderStatus orderStatus;
    private Boolean locked;

    public static OrderDetailBuilder newInstance() {
        return SpringHelper.getBean(OrderDetailBuilder.class);
    }

    public OrderDetailBuilder skuId(int skuId) {
        this.skuId = skuId;
        return this;
    }

    public OrderDetailBuilder price(@NonNull BigDecimal price) {
        this.price = price;
        return this;
    }

    public OrderDetailBuilder orderStatus(@NonNull OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderDetailBuilder id(long id) {
        this.id = id;
        return this;
    }

    public OrderDetailBuilder locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    @Override
    protected OrderDetail doBuild() {
        return new OrderDetail(id, skuId, orderStatus, price, locked);
    }

}
