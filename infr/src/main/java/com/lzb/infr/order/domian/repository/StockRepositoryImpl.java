package com.lzb.infr.order.domian.repository;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.repository.StockRepository;
import com.lzb.infr.order.domian.converter.OrderConverter;
import com.lzb.infr.stock.rpc.InventoryClient;
import com.lzb.infr.stock.rpc.dto.LockStockReqDto;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-08 19:34
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class StockRepositoryImpl implements StockRepository {

    /**
     * 库存服务
     */
    private final InventoryClient inventoryClient;

    @Override
    public LockStockDto lockStock(Order order) {
        LockStockReqDto req = OrderConverter.toLockStockReq(order.getId(), order.getOrderDetails());
        return OrderConverter.toLockStockResult(inventoryClient.lockStock(req));
    }

}
