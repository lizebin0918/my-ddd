package com.lzb.infr.app.order.converter;

import com.lzb.app.order.query.view.OrderView;
import com.lzb.domain.common.valobj.FullName;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.infr.domain.order.converter.OrderConverter;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-22 23:32
 * @author mac
 */
@Component
@RequiredArgsConstructor
public class OrderViewConverter {

    private final OrderRepository orderRepository;

    public OrderView convert(OrderPo orderPo, ) {
        return OrderView.builder()
                .order(orderRepository.getInCache(orderPo.getOrderId()))
                .orderId(orderPo.getOrderId())
                .orderStatus(orderPo.getOrderStatus())
                .fullName(OrderConverter.toFullName(orderPo.getFirstName(), orderPo.getLastName()))
                .country(orderPo.getCountry())
                .email(orderPo.getEmail())
                .build();
    }
}
