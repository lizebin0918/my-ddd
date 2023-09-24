package com.lzb.app.order.query.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lzb.domain.common.valobj.FullName;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import net.minidev.json.annotate.JsonIgnore;

/**
 * 订单View对象<br/>
 * Created on : 2023-09-06 22:44
 * @author mac
 */
@Getter
@Builder
@Jacksonized
public class OrderView {

    @JsonIgnore
    private Order order;

    private Long orderId;

    private OrderStatus orderStatus;

    private FullName fullName;

    private String country;

    private String email;

    /**
     * 订单明细数量
     */
    @JsonProperty
    public int getDetailCount() {
        return order.getOrderDetails().count();
    }

    /**
     * 是否能取消
     */
    @JsonProperty
    public boolean canCancel() {
        return order.canCancel();
    }

}
