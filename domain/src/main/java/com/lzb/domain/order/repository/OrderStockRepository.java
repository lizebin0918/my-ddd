package com.lzb.domain.order.repository;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.dto.LockStockDto;

/**
 * order调product<br/>
 * Created on : 2023-12-08 19:29
 * @author lizebin
 */
public interface OrderStockRepository {

    /**
     * 锁定库存
     * @param order
     * @return
     */
    LockStockDto lockStock(Order order);

}
