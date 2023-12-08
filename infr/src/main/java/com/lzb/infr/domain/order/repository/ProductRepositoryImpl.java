package com.lzb.infr.domain.order.repository;

import com.lzb.adapter.rpc.inverntory.InventoryClient;
import com.lzb.adapter.rpc.inverntory.dto.LockStockReqDto;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.repository.ProductRepository;
import com.lzb.infr.domain.order.converter.OrderConverter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-08 19:34
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

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
