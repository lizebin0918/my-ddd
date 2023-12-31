package com.lzb.infr.order.converter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.aggregation.OrderDetail;
import com.lzb.domain.order.aggregation.OrderDetails;
import com.lzb.domain.order.aggregation.valobj.FullAddressLine;
import com.lzb.domain.order.aggregation.valobj.FullName;
import com.lzb.domain.order.aggregation.valobj.OrderAddress;
import com.lzb.domain.order.dto.SkuStockLockDto;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.po.OrderPo;
import com.lzb.infr.stock.rpc.dto.LockStockDetailReqDto;
import com.lzb.infr.stock.rpc.dto.LockStockDetailRspDto;
import com.lzb.infr.stock.rpc.dto.LockStockReqDto;
import com.lzb.infr.stock.rpc.dto.LockStockRspDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-08-30 23:10
 * @author mac
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public final class OrderConverter {
    public static Order toOrder(OrderPo orderPo,
            List<OrderDetailPo> orderDetailPos) {
        return Order.builder()
                .id(orderPo.getOrderId())
                .version(orderPo.getVersion())
                .orderStatus(orderPo.getOrderStatus())
                .currency(orderPo.getCurrency())
                .exchangeRate(orderPo.getExchangeRate())
                .totalShouldPay(orderPo.getTotalShouldPay())
                .totalActualPay(orderPo.getTotalActualPay())
                .orderAddress(toOrderAddress(orderPo))
                .orderDetails(toOrderDetails(orderDetailPos))
                .build();
    }

    public static OrderDetails toOrderDetails(List<OrderDetailPo> orderDetailPos) {
        return new OrderDetails(orderDetailPos.stream().map(OrderConverter::toOrderDetail).toList());
    }

    public static OrderDetail toOrderDetail(OrderDetailPo orderDetailPo) {
        return OrderDetail.builder()
                .id(orderDetailPo.getId())
                .skuId(orderDetailPo.getSkuId())
                .orderStatus(orderDetailPo.getOrderStatus())
                .price(orderDetailPo.getPrice())
                .locked(orderDetailPo.getLocked())
                .build();
    }

    public static OrderAddress toOrderAddress(OrderPo orderPo) {
        return OrderAddress.builder()
                .fullName(FullName.of(orderPo.getFirstName(), orderPo.getLastName()))
                .fullAddressLine(FullAddressLine.of(orderPo.getAddressLine1(), orderPo.getAddressLine2()))
                .email(orderPo.getEmail())
                .phoneNumber(orderPo.getPhoneNumber())
                .country(orderPo.getCountry())
                .build();
    }

    public static List<LockStockDetailReqDto> toLocakStockDetails(OrderDetails orderDetails) {
        Map<Integer, Long> skuId2Num = StreamEx.of(orderDetails.toStream()).groupingBy(OrderDetail::getSkuId, Collectors.counting());
        return skuId2Num.entrySet().stream().map(entry -> new LockStockDetailReqDto(entry.getKey(), entry.getValue().intValue())).toList();
    }

    public static LockStockReqDto toLockStockReq(long orderId, @NonNull OrderDetails orderDetails) {
        return new LockStockReqDto(Objects.toString(orderId), toLocakStockDetails(orderDetails));
    }

    public static List<SkuStockLockDto> toLockStockResult(LockStockRspDto lockStockRspDto) {
        List<LockStockDetailRspDto> lockedDetails = lockStockRspDto.getLockedDetails();
        return lockedDetails.stream()
                .map(lockedDetail -> new SkuStockLockDto(lockedDetail.getSkuId(), lockedDetail.getLockedNum()))
                .toList();
    }

    public static List<Order> toOrders(List<OrderPo> orders, List<OrderDetailPo> orderDetails) {
        Map<Long, List<OrderDetailPo>> orderId2OrderDetailPos = StreamEx.of(orderDetails).groupingBy(OrderDetailPo::getOrderId);
        return orders.stream().map(order -> toOrder(order, orderId2OrderDetailPos.get(order.getOrderId()))).toList();
    }

}
