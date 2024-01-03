package com.lzb.app.order.cmd.assemble;

import com.lzb.app.order.cmd.dto.PlaceOrderDetailDto;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.app.order.cmd.dto.UpdateAddressDto;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.aggregation.OrderDetail;
import com.lzb.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.domain.order.aggregation.valobj.FullName;
import com.lzb.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.domain.order.aggregation.valobj.OrderStatus;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-17 13:49
 * @author mac
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class OrderAssembler {

    public static Order toOrder(PlaceOrderDto req) {

        OrderAddress orderAddress = OrderAddress.builder()
                .email(req.email())
                .phoneNumber(req.phoneNumber())
                .fullName(FullName.of(req.firstName(), req.lastName()))
                .fullAddressLine(FullAddressLine.of(req.addressLine1(), req.addressLine2()))
                .country(req.country())
                .build();

        Order order = Order.builder()
                .currency(req.currency())
                .exchangeRate(req.exchangeRate())
                .totalShouldPay(req.totalShouldPay())
                .totalActualPay(req.totalActualPay())
                .orderStatus(OrderStatus.WAIT_PAY)
                .orderAddress(orderAddress)
                .build();

        for (PlaceOrderDetailDto detailReq : req.details()) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .skuId(detailReq.skuId())
                    .price(detailReq.price())
                    .orderStatus(OrderStatus.WAIT_PAY)
                    .build();
            order.addOrderDetail(orderDetail);
        }

        return order;
    }

    public static OrderAddress toOrderAddress(UpdateAddressDto updateAddressDto) {
        return OrderAddress.builder()
                .fullName(FullName.of(updateAddressDto.firstName(), updateAddressDto.lastName()))
                .fullAddressLine(FullAddressLine.of(updateAddressDto.addressLine1(), updateAddressDto.addressLine2()))
                .country(updateAddressDto.country())
                .email(updateAddressDto.email())
                .phoneNumber(updateAddressDto.phoneNumber())
                .build();
    }

}
