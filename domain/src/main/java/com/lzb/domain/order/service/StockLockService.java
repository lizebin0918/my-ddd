package com.lzb.domain.order.service;

import java.util.List;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.dto.SkuStockLockDto;

/**
 * 库存锁定<br/>
 * Created on : 2024-01-05 13:50
 * @author lizebin
 */
public interface StockLockService {

    /**
     * 锁定库存
     * @param order
     * @return
     */
    List<SkuStockLockDto> lockStock(Order order);

}
