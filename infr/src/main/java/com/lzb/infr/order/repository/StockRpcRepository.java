package com.lzb.infr.order.repository;

import java.util.List;

import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.dto.SkuStockLockDto;
import com.lzb.domain.order.repository.StockRepository;
import com.lzb.infr.order.converter.OrderConverter;
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
public class StockRpcRepository implements StockRepository {

    /**
     * 库存服务
     */
    private final InventoryClient inventoryClient;

    @Override
    public List<SkuStockLockDto> lockStock(Order order) {
        LockStockReqDto req = OrderConverter.toLockStockReq(order.getId(), order.getOrderDetails());
        return OrderConverter.toLockStockResult(inventoryClient.lockStock(req));
    }

}
