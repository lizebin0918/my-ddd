package com.lzb.infr.domain.order.gateway;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.lzb.adapter.rpc.inverntory.InventoryClient;
import com.lzb.adapter.rpc.inverntory.dto.LockStockReqDto;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.dto.SkuInfoDto;
import com.lzb.domain.order.dto.SkuOnSaleDto;
import com.lzb.domain.order.gateway.ProductGateway;
import com.lzb.infr.domain.order.converter.OrderConverter;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * <br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    /**
     * 库存服务
     */
    private final InventoryClient inventoryClient;

    @Override
    public List<SkuOnSaleDto> onSale(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuOnSaleDto(skuId, true)).toList();
    }

    @Override
    public List<SkuInfoDto> list(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuInfoDto(skuId, "name-" + skuId, "picUrl-" + skuId)).toList();
    }

    @Override
    public List<SkuOnSaleDto> onSale(List<Integer> skuIds) {
        return Collections.emptyList();
    }

    @Override
    public LockStockDto lockStock(Order order) {
        LockStockReqDto req = OrderConverter.toLockStockReq(order.getId(), order.getOrderDetails());
        return OrderConverter.toLockStockResult(inventoryClient.lockStock(req));
    }

}
