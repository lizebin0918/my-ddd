package com.lzb.infr.order.converter;

import com.lzb.app.order.query.vo.OrderView;
import com.lzb.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-22 23:32
 * @author mac
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class OrderViewConverter {

    private final OrderRepository orderRepository;

    public OrderView convert(long orderId) {
        return orderRepository.getInCache(orderId)
                .map(order -> OrderView.builder().order(order).build())
                .orElseThrow();
    }
}
