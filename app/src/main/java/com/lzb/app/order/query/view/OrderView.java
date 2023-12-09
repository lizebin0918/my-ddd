package com.lzb.app.order.query.view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.domain.order.valobj.FullName;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * 订单View对象<br/>
 * Created on : 2023-09-06 22:44
 * @author mac
 */
@Getter
@Builder
@Jacksonized
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderView {

    @JsonIgnore
    private Order order;

    public long getOrderId() {
        return order.getId();
    }

    public OrderStatus getOrderStatus() {
        return order.getOrderStatus();
    }

    public FullName getFullName() {
        return order.getOrderAddress().getFullName();
    }

    public String getCountry() {
        return order.getOrderAddress().getCountry();
    }

    public String getEmail() {
        return order.getOrderAddress().getEmail();
    }

    /**
     * 订单明细数量
     */
    public int getDetailCount() {
        return order.getOrderDetails().count();
    }

    /**
     * 是否能取消
     */
    public boolean getCanCancel() {
        return order.canCancel();
    }

}
