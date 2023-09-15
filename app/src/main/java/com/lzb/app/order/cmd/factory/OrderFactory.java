package com.lzb.app.order.cmd.factory;


import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.builder.OrderBuilder;
import com.lzb.domain.order.aggregate.OrderDetail;
import com.lzb.domain.order.aggregate.builder.OrderDetailBuilder;
import com.lzb.domain.order.enums.OrderStatus;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;

/**
 * 订单聚合根工厂<br/>
 * Created on : 2023-09-09 16:38
 * @author lizebin
 */
@Component
public class OrderFactory {

    @Resource
    private IdGenerator idGenerator;

    public Order create(PlaceOrderReq req) {
        long orderId = idGenerator.id();
        OrderBuilder orderBuilder = OrderBuilder.newInstance()
                .orderId(orderId)
                .currency(req.currency())
                .exchangeRate(req.exchangeRate())
                .totalShouldPay(req.totalShouldPay())
                .totalActualPay(req.totalActualPay())
                .email(req.email())
                .phoneNumber(req.phoneNumber())
                .firstName(req.firstName())
                .lastName(req.lastName())
                .addressLine1(req.addressLine1())
                .addressLine2(req.addressLine2())
                .country(req.country())
                .orderStatus(OrderStatus.WAIT_PAY);

        for (PlaceOrderDetailReq detailReq : req.details()) {
            OrderDetail orderDetail = OrderDetailBuilder.newInstance()
                    .orderId(orderId)
                    .skuId(detailReq.skuId())
                    .price(detailReq.price())
                    .orderStatus(OrderStatus.WAIT_PAY)
                    .build();
            orderBuilder.addOrderDetail(orderDetail);
        }

        return orderBuilder.build();
    }


}
