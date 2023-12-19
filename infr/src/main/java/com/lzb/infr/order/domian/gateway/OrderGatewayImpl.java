package com.lzb.infr.order.domian.gateway;

import java.util.Set;

import com.lzb.domain.order.gateway.OrderGateway;

/**
 * 订单gateway实现<br/>
 * Created on : 2023-12-19 10:00
 * @author lizebin
 */
public class OrderGatewayImpl implements OrderGateway {

    @Override
    public Set<Long> queryByEmail(String email) {
        return Set.of();
    }
}
