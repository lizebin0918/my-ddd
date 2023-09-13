package com.lzb.domain.order.service;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockResult;
import com.lzb.domain.order.gateway.ProductGateway;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * 库存操作<br/>
 * Created on : 2023-09-12 23:18
 * @author mac
 */
@Component
@AllArgsConstructor
public class StockHandler {

    private final ProductGateway productGateway;

    /**
     * 锁定库存
     * @param order
     */
    public void lockStock(Order order) {
        LockStockResult lockStockResult = productGateway.lockStock(order);
        order.updateStockStatus(lockStockResult);
    }

}
