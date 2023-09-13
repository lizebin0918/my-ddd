package com.lzb.app.order.cmd;

import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.app.order.cmd.factory.OrderFactory;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.domain.order.service.StockHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 生单<br/>
 * Created on : 2023-09-05 19:53
 * @author lizebin
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class PlaceOrderAppService {

    private final OrderRepository orders;

    private final OrderFactory orderFactory;

    private final StockHandler stockHandler;

    /**
     * 生单
     * @param req
     * @return
     */
    public long placeOrder(PlaceOrderReq req) {
        log.info("place order: {}", req);

        // 生单
        Order order = orderFactory.create(req);
        order.place();

        // 锁库存
        stockHandler.lockStock(order);

        orders.add(order);
        return order.getId();
    }

}
