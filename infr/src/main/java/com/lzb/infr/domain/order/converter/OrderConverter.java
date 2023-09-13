package com.lzb.infr.domain.order.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.lzb.adapter.rpc.inverntory.dto.LockStockReq;
import com.lzb.adapter.rpc.inverntory.dto.LockStockReqDetail;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRsp;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRspDetail;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderAddress;
import com.lzb.domain.order.aggregate.OrderDetail;
import com.lzb.domain.order.aggregate.OrderDetails;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.infr.domain.order.persistence.po.OrderDetailPo;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import lombok.NonNull;
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
                orderDetailPo.getSkuId(), orderDetailPo.getOrderStatus(), orderDetailPo.getPrice(), orderDetailPo.getLocked());
    }

    public static OrderAddress toOrderAddress(OrderPo orderPo) {
        return new OrderAddress(orderPo.getOrderId(),
                new FullName(orderPo.getFirstName(), orderPo.getLastName()),
                new FullAddressLine(orderPo.getAddressLine1(), orderPo.getAddressLine2()),
                orderPo.getEmail(), orderPo.getPhoneNumber(), orderPo.getCountry());

    }

    public static List<LockStockReqDetail> toLocakStockDetails(OrderDetails orderDetails) {
        Map<Integer, Integer> skuId2Num = new HashMap<>();
        orderDetails.forEach(orderDetail -> skuId2Num.merge(orderDetail.getSkuId(), 1, Integer::sum));
        return skuId2Num.entrySet().stream().map(entry -> new LockStockReqDetail(entry.getKey(), entry.getValue())).toList();
    }

    public static LockStockReq toLockStockReq(@NonNull Order order) {
        return new LockStockReq(Objects.toString(order.getId()), toLocakStockDetails(order.getOrderDetails()));
    }

    public static LockStockDto toLockStockResult(LockStockRsp lockStockRsp) {
        List<LockStockRspDetail> lockedDetails = lockStockRsp.getLockedDetails();
        return new LockStockDto(lockedDetails.stream()
                .map(lockedDetail -> new LockStockDto.LockStockDetailDto(lockedDetail.getSkuId(), lockedDetail.getLockedNum()))
                .toList());
    }
}
