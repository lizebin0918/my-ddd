package com.lzb.app.order.cmd;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.BaseIntegrationTest;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderBuilder;
import com.lzb.domain.order.aggregate.OrderDetailBuilder;
import com.lzb.domain.order.enums.OrderStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-09 14:23
 * @author lizebin
 */
class PlaceOrderServiceIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private IdGenerator idGenerator;

    @Test
    @Disabled
    @DisplayName("测试OrderDetailBuilder")
    void should_place_order() {

        PlaceOrderReq req = new PlaceOrderReq("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailReq(1, BigDecimal.ONE)));

        long orderId = 1L;
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

        for (int i = 0; i < req.details().size(); i++) {
            PlaceOrderDetailReq detailReq = req.details().get(i);
            OrderDetailBuilder orderDetailBuilder = OrderDetailBuilder.newInstance()
                    .id(orderId + i + 1)
                    .orderId(orderId)
                    .skuId(detailReq.skuId())
                    .price(detailReq.price())
                    .orderStatus(OrderStatus.WAIT_PAY);
            orderBuilder.addDetailBuilder(orderDetailBuilder);
        }

        Order order = orderBuilder.build();
        assertJSON(order);
    }

}