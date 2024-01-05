package com.lzb.domain.order.service;

import java.util.List;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.dto.SkuStockLockDto;
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

    private final StockLockService stockLockService;

    /**
     * 锁定库存
     * 这个动作不能声明成接口，最终的锁库存结果，需要更新到订单上，这一步算业务逻辑
     * @param order
     */
    public void lockStock(Order order) {
        List<SkuStockLockDto> skuStockLockDtos = stockLockService.lockStock(order);
        order.updateSkuLockStock(skuStockLockDtos);
    }

}
