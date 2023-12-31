package com.lzb.infr.order.converter;

import java.util.List;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.aggregation.OrderAddress;
import com.lzb.domain.order.aggregation.OrderDetail;
import com.lzb.domain.order.aggregation.OrderDetails;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.po.OrderPo;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-24 22:44
 * @author mac
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class OrderPoConverter {

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

    public static List<OrderDetailPo> toOrderDetailPos(long orderId, OrderDetails orderDetails) {
        return orderDetails.toStream().map(d -> toOrderDetailPo(orderId, d)).toList();
    }

    private static OrderDetailPo toOrderDetailPo(long orderId, OrderDetail orderDetail) {
        OrderDetailPo orderDetailPo = new OrderDetailPo();
        orderDetailPo.setId(orderDetail.getId());
        orderDetailPo.setOrderId(orderId);
        orderDetailPo.setSkuId(orderDetail.getSkuId());
        orderDetailPo.setOrderStatus(orderDetail.getOrderStatus());
        orderDetailPo.setPrice(orderDetail.getPrice());
        orderDetailPo.setLocked(orderDetail.getLocked());
        return orderDetailPo;
    }

    public static void setOrderAddress(OrderPo orderPo, OrderAddress orderAddress) {
        orderPo.setEmail(orderAddress.getEmail());
        orderPo.setPhoneNumber(orderAddress.getPhoneNumber());
        orderPo.setCountry(orderAddress.getCountry());

        FullName fullName = orderAddress.getFullName();
        orderPo.setFirstName(fullName.getFirstName());
        orderPo.setLastName(fullName.getLastName());

        FullAddressLine fullAddressLine = orderAddress.getFullAddressLine();
        orderPo.setAddressLine1(fullAddressLine.getAddressLine1());
        orderPo.setAddressLine2(fullAddressLine.getAddressLine2());
    }

}
