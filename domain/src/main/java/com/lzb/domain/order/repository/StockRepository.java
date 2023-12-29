package com.lzb.domain.order.repository;

import java.util.List;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.dto.SkuStockLock;

/**
 * order调product<br/>
 * Created on : 2023-12-08 19:29
 * @author lizebin
 */
public interface StockRepository {

    /**
     * 锁定库存
     * @param order
     * @return
     */
    List<SkuStockLock> lockStock(Order order);

}
