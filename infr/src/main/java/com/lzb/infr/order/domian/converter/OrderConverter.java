package com.lzb.infr.order.domian.converter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.aggregation.OrderAddress;
import com.lzb.domain.order.aggregation.OrderDetail;
import com.lzb.domain.order.aggregation.OrderDetails;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.infr.order.domian.persistence.po.OrderDetailPo;
import com.lzb.infr.order.domian.persistence.po.OrderPo;
import com.lzb.infr.stock.rpc.dto.LockStockDetailReqDto;
import com.lzb.infr.stock.rpc.dto.LockStockDetailRspDto;
import com.lzb.infr.stock.rpc.dto.LockStockReqDto;
import com.lzb.infr.stock.rpc.dto.LockStockRspDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-08-30 23:10
 * @author mac
 */
@Component
@RequiredArgsConstructor
public final class OrderConverter {
    public static Order toOrder(OrderPo orderPo,
            List<OrderDetailPo> orderDetailPos) {
        return new Order(orderPo.getOrderId(), orderPo.getVersion(), orderPo.getOrderStatus(),
                orderPo.getCurrency(), orderPo.getExchangeRate(), orderPo.getTotalShouldPay(),
                orderPo.getTotalActualPay(), toOrderAddress(orderPo), toOrderDetails(orderDetailPos));
    }

    public static OrderDetails toOrderDetails(List<OrderDetailPo> orderDetailPos) {
        return new OrderDetails(orderDetailPos.stream().map(OrderConverter::toOrderDetail).toList());
    }

    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        return new OrderDetail(orderDetailPo.getId(),
                orderDetailPo.getSkuId(), orderDetailPo.getOrderStatus(), orderDetailPo.getPrice(), orderDetailPo.getLocked());
    }

    public static OrderAddress toOrderAddress(OrderPo orderPo) {
        return new OrderAddress(orderPo.getOrderId(),
                FullName.of(orderPo.getFirstName(), orderPo.getLastName()),
                FullAddressLine.of(orderPo.getAddressLine1(), orderPo.getAddressLine2()),
                orderPo.getEmail(), orderPo.getPhoneNumber(), orderPo.getCountry());

    }

    public static List<LockStockDetailReqDto> toLocakStockDetails(OrderDetails orderDetails) {
        Map<Integer, Long> skuId2Num = StreamEx.of(orderDetails.toStream()).groupingBy(OrderDetail::getSkuId, Collectors.counting());
        return skuId2Num.entrySet().stream().map(entry -> new LockStockDetailReqDto(entry.getKey(), entry.getValue().intValue())).toList();
    }

    public static LockStockReqDto toLockStockReq(long orderId, @NonNull OrderDetails orderDetails) {
        return new LockStockReqDto(Objects.toString(orderId), toLocakStockDetails(orderDetails));
    }

    public static LockStockDto toLockStockResult(LockStockRspDto lockStockRspDto) {
        List<LockStockDetailRspDto> lockedDetails = lockStockRspDto.getLockedDetails();
        return new LockStockDto(lockedDetails.stream()
                .map(lockedDetail -> new LockStockDto.LockStockDetailDto(lockedDetail.getSkuId(), lockedDetail.getLockedNum()))
                .toList());
    }

    public static List<Order> toOrders(List<OrderPo> orders, List<OrderDetailPo> orderDetails) {
        Map<Long, List<OrderDetailPo>> orderId2OrderDetailPos = StreamEx.of(orderDetails).groupingBy(OrderDetailPo::getOrderId);
        return orders.stream().map(order -> toOrder(order, orderId2OrderDetailPos.get(order.getOrderId()))).toList();
    }

}
