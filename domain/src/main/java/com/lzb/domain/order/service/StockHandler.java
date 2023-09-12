package com.lzb.domain.order.service;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockResult;

/**
 * 库存操作<br/>
 * Created on : 2023-09-12 23:18
 * @author mac
 */
public interface StockHandler {

    /**
     * 锁定库存
     * @param order
     */
    LockStockResult lockStock(Order order);

}
