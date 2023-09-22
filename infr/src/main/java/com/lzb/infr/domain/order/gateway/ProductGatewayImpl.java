package com.lzb.infr.domain.order.gateway;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.lzb.adapter.rpc.inverntory.InventoryClient;
import com.lzb.adapter.rpc.inverntory.dto.LockStockReqDto;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.dto.SkuDto;
import com.lzb.domain.order.gateway.ProductGateway;
import com.lzb.infr.domain.order.converter.OrderConverter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * <br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Validated
@Component
@AllArgsConstructor(onConstructor = @__(@Lazy))
public class ProductGatewayImpl implements ProductGateway {

    /**
     * 库存服务
     */
    private final InventoryClient inventoryClient;

    @Override
    public List<SkuDto> onSale(int... skuIds) {
        return IntStream.of(skuIds).mapToObj(skuId -> new SkuDto(skuId, true)).toList();
    }

    @Override
    public List<SkuDto> onSale(List<Integer> skuIds) {
        System.out.println("ProductGatewayImpl.onSale");
        return Collections.emptyList();
    }

    @Override
    public LockStockDto lockStock(Order order) {
        LockStockReqDto req = OrderConverter.toLockStockReq(order.getId(), order.getOrderDetails());
        return OrderConverter.toLockStockResult(inventoryClient.lockStock(req));
    }

}
