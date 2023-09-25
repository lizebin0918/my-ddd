package com.lzb.app.order.query.view;

import com.lzb.domain.common.valobj.FullName;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.enums.OrderStatus;
import net.minidev.json.annotate.JsonIgnore;

/**
 * <br/>
 * Created on : 2023-09-06 22:55
 * @author mac
 */
public class OrderDetailView {

    @JsonIgnore
    private Order order;

    private OrderAddressView orderAddressView;

}
