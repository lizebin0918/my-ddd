package com.lzb.infr.order.converter;

import java.util.List;

import com.lzb.domain.order.Order;
import com.lzb.domain.order.OrderAddress;
import com.lzb.domain.order.OrderDetail;
import com.lzb.domain.order.OrderDetails;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.po.OrderPo;
import lombok.experimental.UtilityClass;

/**
 * <br/>
 * Created on : 2023-08-30 23:10
 * @author mac
 */
@UtilityClass
public final class OrderConverter {

    public static OrderPo toOrderDo(Order order) {
        return null;
    }

    public static Order toOrder(OrderPo orderPo,
            List<OrderDetailPo> orderDetailPos) {
        return Order.builder()
                .id(orderPo.getOrderId())
                .orderStatus(orderPo.getOrderStatus())
                .currency(orderPo.getCurrency())
                .exchangeRate(orderPo.getExchangeRate())
                .totalActualPay(orderPo.getTotalActualPay())
                .totalShouldPay(orderPo.getTotalShouldPay())
                .orderAddress(toOrderAddress(orderPo))
                .orderDetails(toOrderDetails(orderDetailPos))
                .version(orderPo.getVersion())
                .build();
    }

    public static OrderDetails toOrderDetails(List<OrderDetailPo> orderDetailPos) {
        return new OrderDetails(orderDetailPos.stream()
                .map(OrderConverter::toOrderDetail)
                .toList());
    }

    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        return OrderDetail.builder()
                .orderId(orderDetailPo.getOrderId())
                .orderStatus(orderDetailPo.getOrderStatus())
                .price(orderDetailPo.getPrice())
                .skuId(orderDetailPo.getSkuId())
                .id(orderDetailPo.getId())
                .build();
    }

    public static OrderAddress toOrderAddress(OrderPo orderPo) {
        return OrderAddress.builder()
                .orderId(orderPo.getOrderId())
                .country(orderPo.getCountry())
                .email(orderPo.getEmail())
                .fullName(new FullName(orderPo.getFirstName(), orderPo.getLastName()))
                .fullAddressLine(new FullAddressLine(orderPo.getAddressLine1(), orderPo.getAddressLine2()))
                .phoneNumber(orderPo.getPhoneNumber())
                .build();

    }

}
