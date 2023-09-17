package com.lzb.app.order.cmd.assemble;

import java.util.function.LongSupplier;

import com.lzb.app.order.cmd.dto.PlaceOrderDetailDto;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.app.order.cmd.dto.UpdateOrderAddressDto;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderAddress;
import com.lzb.domain.order.aggregate.OrderDetail;
import com.lzb.domain.order.aggregate.builder.OrderAddressBuilder;
import com.lzb.domain.order.aggregate.builder.OrderBuilder;
import com.lzb.domain.order.aggregate.builder.OrderDetailBuilder;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.experimental.UtilityClass;

/**
 * <br/>
 * Created on : 2023-09-17 13:49
 * @author mac
 */
@UtilityClass
public class OrderAssembler {

    public static Order toOrder(PlaceOrderDto req, LongSupplier idProvider) {
        long orderId = idProvider.getAsLong();
        OrderBuilder orderBuilder = OrderBuilder.newInstance()
                .orderId(orderId)
                .currency(req.currency())
                .exchangeRate(req.exchangeRate())
                .totalShouldPay(req.totalShouldPay())
                .totalActualPay(req.totalActualPay())
                .orderStatus(OrderStatus.WAIT_PAY);

        OrderAddress orderAddress = OrderAddressBuilder.newInstance().email(req.email()).phoneNumber(req.phoneNumber())
                .firstName(req.firstName()).lastName(req.lastName()).addressLine1(req.addressLine1())
                .addressLine2(req.addressLine2()).country(req.country()).build();
        orderBuilder.orderAddress(orderAddress);

        for (PlaceOrderDetailDto detailReq : req.details()) {
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

    public static OrderAddress toOrderAddress(UpdateOrderAddressDto updateOrderAddress) {
        return OrderAddressBuilder.newInstance()
                .email(updateOrderAddress.email())
                .phoneNumber(updateOrderAddress.phoneNumber())
                .firstName(updateOrderAddress.firstName())
                .lastName(updateOrderAddress.lastName())
                .addressLine1(updateOrderAddress.addressLine1())
                .addressLine2(updateOrderAddress.addressLine2())
                .country(updateOrderAddress.country())
                .build();
    }

}
