package com.lzb.infr.domain.order.gateway;

import java.util.List;
import java.util.stream.IntStream;

import com.lzb.adapter.rpc.InventoryClient;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockResult;
import com.lzb.domain.order.dto.Sku;
import com.lzb.domain.order.gateway.ProductGateway;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class ProductGatewayImpl implements ProductGateway {

    /**
     * 库存服务
     */
    private final InventoryClient inventoryClient;

    @Override
    public List<Sku> onSale(int... skuIds) {
        return IntStream.of(skuIds).mapToObj(skuId -> new Sku(skuId, true)).toList();
    }

    @Override
    public LockStockResult lockStock(Order order) {
        // return inventoryClient.lockStock();
        return null;
    }
}
