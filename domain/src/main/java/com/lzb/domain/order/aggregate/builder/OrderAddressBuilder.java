package com.lzb.domain.order.aggregate.builder;

import com.lzb.component.helper.SpringHelper;
import com.lzb.domain.common.aggregate.BaseBuilder;
import com.lzb.domain.order.aggregate.OrderAddress;

/**
 * <br/>
 * Created on : 2023-09-15 22:08
 * @author lizebin
 */
public class OrderAddressBuilder extends BaseBuilder<OrderAddress> {

    public static OrderAddressBuilder newInstance() {
        return SpringHelper.getBean(OrderAddressBuilder.class);
    }

    @Override
    protected OrderAddress doBuild() {
        return null;
    }
}
