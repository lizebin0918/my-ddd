package com.lzb.app.order.query.view;

import com.lzb.domain.common.valobj.FullName;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.enums.OrderStatus;

public class OrderViewBuilder {

    private final OrderView orderView = new OrderView();

    OrderViewBuilder() {
    }


    public OrderViewBuilder order(final Order order) {
        orderView.setOrder(order);
        return this;
    }


    public OrderViewBuilder orderId(final Long orderId) {
        orderView.setOrderId(orderId);
        return this;
    }


    public OrderViewBuilder orderStatus(final OrderStatus orderStatus) {
        orderView.setOrderStatus(orderStatus);
        return this;
    }


    public OrderViewBuilder fullName(final FullName fullName) {
        orderView.setFullName(fullName);
        return this;
    }


    public OrderViewBuilder country(final String country) {
        orderView.setCountry(country);
        return this;
    }


    public OrderViewBuilder email(final String email) {
        orderView.setEmail(email);
        return this;
    }

    public OrderView build() {
        orderView.setDetailCount((int) orderView.getOrder().getOrderDetails().toStream().count());
        orderView.setCanCancel(orderView.getOrder().canCancel());
        return orderView;
    }

}