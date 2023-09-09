package com.lzb.app.order.cmd;

import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.app.order.cmd.factory.OrderFactory;
import com.lzb.component.dto.MyReponse;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.repository.OrderRepository;
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
public class PlaceOrderService {

    private final OrderRepository orders;

    private final OrderFactory orderFactory;

    /**
     * 生单
     * @param req
     * @return
     */
    public MyReponse<Long> placeOrder(PlaceOrderReq req) {
        log.info("place order: {}", req);
        Order order = orderFactory.create(req);
        orders.add(order);
        return MyReponse.success(order.getId());
    }

}
