package com.lzb.app.order.query.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzb.domain.common.valobj.FullAddressLine;
import com.lzb.domain.common.valobj.FullName;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.SkuDto;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

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

    @JsonIgnore
    private OrderDetailViewContext orderDetailViewContext;

    ///////////////////////////////////////////////////////////////////////////
    // 订单相关
    ///////////////////////////////////////////////////////////////////////////

    public long getOrderId() {
        return order.getId();
    }

    public OrderStatus getOrderStatus() {
        return order.getOrderStatus();
    }

    public BigDecimal getTotalActualPay() {
        return order.getTotalActualPay();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 订单地址
    ///////////////////////////////////////////////////////////////////////////

    public OrderAddress getOrderAddress() {
        com.lzb.domain.order.aggregate.OrderAddress orderAddress = order.getOrderAddress();
        return OrderAddress.builder()
                .fullName(orderAddress.getFullName())
                .fullAddressLine(orderAddress.getFullAddressLine())
                .email(orderAddress.getEmail())
                .phoneNumber(orderAddress.getPhoneNumber())
                .country(orderAddress.getCountry())
                .build();
    }

    @Getter
    @Builder
    public static class OrderAddress {

        private FullName fullName;

        private FullAddressLine fullAddressLine;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 电话
         */
        private String phoneNumber;

        /**
         * 国家
         */
        private String country;

    }

    ///////////////////////////////////////////////////////////////////////////
    // 商品信息
    ///////////////////////////////////////////////////////////////////////////

    public List<OrderDetail> getOrderDetails() {
        Map<Integer, SkuDto> skuId2SkuDto = orderDetailViewContext.getSkuId2SkuDto();
        return order.getOrderDetails().toStream().map(orderDetail -> {
            return OrderDetail.builder()
                    .orderStatus(orderDetail.getOrderStatus())
                    .price(orderDetail.getPrice())
                    .locked(orderDetail.getLocked())
                    .picUrl(Optional.ofNullable(skuId2SkuDto.get(orderDetail.getSkuId())).map(SkuDto::picUrl).orElse(null))
                    .build();
        }).toList();
    }

    @Getter
    @Builder
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
