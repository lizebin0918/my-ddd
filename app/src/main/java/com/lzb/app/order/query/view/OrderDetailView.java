package com.lzb.app.order.query.view;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import net.minidev.json.annotate.JsonIgnore;

/**
 * <br/>
 * Created on : 2023-09-06 22:55
 * @author mac
 */
@Builder
@Jacksonized
@AllArgsConstructor
@Getter
public class OrderDetailView {

    @JsonIgnore
    private Order order;

    ///////////////////////////////////////////////////////////////////////////
    // 订单相关
    ///////////////////////////////////////////////////////////////////////////

    public long getOrderId() {
        return order.getId();
    }

    public OrderStatus getOrderStatus() {
        return order.getOrderStatus();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 订单地址
    ///////////////////////////////////////////////////////////////////////////

    public OrderAddress getOrderAddress() {
        return null;
    }

    public static class OrderAddress {

    }

    ///////////////////////////////////////////////////////////////////////////
    // 商品信息
    ///////////////////////////////////////////////////////////////////////////

    public List<OrderDetail> getOrderDetails() {
        return null;
    }

    public static class OrderDetail {
        private OrderStatus orderStatus;

        private BigDecimal price;

        /**
         * null:未知
         * true:锁定库存
         * false:缺货
         */
        private Boolean locked;

        /**
         * 图片链接
         */
        private String picUrl;
    }




}
