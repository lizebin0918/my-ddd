package com.lzb.infr.app.order.converter;

import com.lzb.app.order.query.view.OrderView;
import com.lzb.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

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

    public OrderView convert(long orderId) {
        return OrderView.builder().order(orderRepository.getInCache(orderId)).build();
    }
}
