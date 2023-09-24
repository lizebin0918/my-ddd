package com.lzb.app.order.cmd.assemble;

import com.lzb.app.order.cmd.dto.PlaceOrderDetailDto;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.app.order.cmd.dto.UpdateAddressDto;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderAddress;
import com.lzb.domain.order.aggregate.OrderDetail;
import com.lzb.domain.order.aggregate.builder.OrderAddressBuilder;
import com.lzb.domain.order.aggregate.builder.OrderBuilder;
import com.lzb.domain.order.aggregate.builder.OrderDetailBuilder;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-17 13:49
 * @author mac
 */
@Component
@RequiredArgsConstructor
public class OrderAssembler {

    public static Order toOrder(PlaceOrderDto req) {
        OrderBuilder orderBuilder = OrderBuilder.newInstance()
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
                    .skuId(detailReq.skuId())
                    .price(detailReq.price())
                    .orderStatus(OrderStatus.WAIT_PAY)
                    .build();
            orderBuilder.addOrderDetail(orderDetail);
        }

        return orderBuilder.build();
    }

    public static OrderAddress toOrderAddress(UpdateAddressDto updateAddressDto) {
        return OrderAddressBuilder.newInstance()
                .id(updateAddressDto.orderId())
                .addressLine1(updateAddressDto.addressLine1())
                .addressLine2(updateAddressDto.addressLine2())
                .country(updateAddressDto.country())
                .email(updateAddressDto.email())
                .firstName(updateAddressDto.firstName())
                .lastName(updateAddressDto.lastName())
                .phoneNumber(updateAddressDto.phoneNumber())
                .build();
    }

}
