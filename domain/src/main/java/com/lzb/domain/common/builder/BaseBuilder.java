package com.lzb.domain.common.builder;

import com.lzb.component.helper.SpringHelper;
import com.lzb.domain.order.aggregate.OrderBuilder;

/**
 * <br/>
 * Created on : 2023-09-08 14:18
 * @author lizebin
 */
public abstract class BaseBuilder<T> {
    public static <T> T newInstance() {
        return (T) SpringHelper.getBean(OrderBuilder.class);
    }

}
