package com.lzb.infr.domain.order.converter;

import java.util.List;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderAddress;
import com.lzb.domain.order.aggregate.OrderDetail;
import com.lzb.domain.order.aggregate.OrderDetails;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.infr.domain.order.persistence.po.OrderDetailPo;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import lombok.experimental.UtilityClass;

/**
 * <br/>
 * Created on : 2023-08-30 23:10
 * @author mac
 */
@UtilityClass
public final class OrderConverter {

    public static OrderPo toOrderPo(Order order) {
        OrderPo orderPo = new OrderPo();
        orderPo.setOrderId(order.getId());
        orderPo.setOrderStatus(order.getOrderStatus());
        orderPo.setCurrency(order.getCurrency());
        orderPo.setExchangeRate(order.getExchangeRate());
        orderPo.setTotalShouldPay(order.getTotalShouldPay());
        orderPo.setTotalActualPay(order.getTotalActualPay());
        orderPo.setVersion(order.getVersion());
        setOrderAddress(orderPo, order.getOrderAddress());
        return orderPo;
    }

    public static List<OrderDetailPo> toOrderDetailPos(OrderDetails orderDetails) {
        return orderDetails.toStream().map(OrderConverter::toOrderDetailPo).toList();
    }

    private static OrderDetailPo toOrderDetailPo(OrderDetail orderDetail) {
        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setId(orderDetail.getId());
        orderDetailPo.setOrderId(orderDetail.getOrderId());
        orderDetailPo.setSkuId(orderDetail.getSkuId());
        orderDetailPo.setOrderStatus(orderDetail.getOrderStatus());
        orderDetailPo.setPrice(orderDetail.getPrice());
        return orderDetailPo;
    }

    public static void setOrderAddress(OrderPo orderPo, OrderAddress orderAddress) {
        orderPo.setEmail(orderAddress.getEmail());
        orderPo.setPhoneNumber(orderAddress.getPhoneNumber());
        orderPo.setCountry(orderAddress.getCountry());

        FullName fullName = orderAddress.getFullName();
        orderPo.setFirstName(fullName.firstName());
        orderPo.setLastName(fullName.lastName());

        FullAddressLine fullAddressLine = orderAddress.getFullAddressLine();
        orderPo.setAddressLine1(fullAddressLine.addressLine1());
        orderPo.setAddressLine2(fullAddressLine.addressLine2());
    }

    public static Order toOrder(OrderPo orderPo,
            List<OrderDetailPo> orderDetailPos) {
        return new Order(orderPo.getOrderId(), orderPo.getVersion(), orderPo.getOrderStatus(),
                orderPo.getCurrency(), orderPo.getExchangeRate(), orderPo.getTotalShouldPay(),
                orderPo.getTotalActualPay(), toOrderAddress(orderPo), toOrderDetails(orderDetailPos));
    }

    public static OrderDetails toOrderDetails(List<OrderDetailPo> orderDetailPos) {
        return new OrderDetails(orderDetailPos.stream()
                .map(OrderConverter::toOrderDetail)
                .toList());
    }

    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        return new OrderDetail(orderDetailPo.getId(), orderDetailPo.getOrderId(),
                orderDetailPo.getSkuId(), orderDetailPo.getOrderStatus(), orderDetailPo.getPrice());
    }

    public static OrderAddress toOrderAddress(OrderPo orderPo) {
        return new OrderAddress(orderPo.getOrderId(),
                new FullName(orderPo.getFirstName(), orderPo.getLastName()),
                new FullAddressLine(orderPo.getAddressLine1(), orderPo.getAddressLine2()),
                orderPo.getEmail(), orderPo.getPhoneNumber(), orderPo.getCountry());

    }

}
