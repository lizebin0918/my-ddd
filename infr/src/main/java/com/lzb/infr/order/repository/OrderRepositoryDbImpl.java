package com.lzb.infr.order.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import com.lzb.domain.order.Order;
import com.lzb.domain.order.OrderRepository;
import com.lzb.infr.common.BaseRepository;
import com.lzb.infr.order.converter.OrderConverter;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.po.OrderPo;
import com.lzb.infr.order.persistence.service.impl.OrderDetailPoService;
import com.lzb.infr.order.persistence.service.impl.OrderPoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-08-30 23:08
 * @author mac
 */
@Slf4j
@Repository(OrderRepositoryDbImpl.BEAN_NAME)
@RequiredArgsConstructor
public class OrderRepositoryDbImpl extends BaseRepository<Order> implements OrderRepository {

    public static final String BEAN_NAME = "orderRepositoryDbImpl";

    private final OrderPoService orderPoService;

    private final OrderDetailPoService orderDetailPoService;

    @Override
    protected LongSupplier doAdd(Order aggregate) {
        return null;
    }

    @Override
    protected Runnable doUpdate(Order aggregate) {
        return null;
    }

    @Override
    public Optional<Order> get(long id) {
        Optional<OrderPo> orderOpt = orderPoService.getOptById(id);
        if (orderOpt.isEmpty()) {
            return Optional.empty();
        }
        OrderPo orderPo = orderOpt.get();
        List<OrderDetailPo> orderDetailPos = orderDetailPoService.listByOrderId(id);
        return Optional.of(OrderConverter.toOrder(orderPo, orderDetailPos));
    }
}
