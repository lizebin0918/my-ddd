package com.lzb.domain.order.gateway;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.dto.SkuInfoDto;
import com.lzb.domain.order.dto.SkuOnSaleDto;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

/**
 * <br/>
 * Created on : 2023-09-08 09:06
 * @author mac
 */
@Validated
public interface ProductGateway {

    /**
     * 查询上架的sku状态
     * @param skuIds
     * @return
     */
    List<SkuOnSaleDto> onSale(@NotEmpty Set<Integer> skuIds);

    /**
     * 查询sku信息
     *
     * @param skuIds
     * @return
     */
    List<SkuInfoDto> list(@NotEmpty Set<Integer> skuIds);

    /**
     * 测试非空
     * @param skuIds
     * @return
     */
    List<SkuOnSaleDto> onSale(@NotEmpty List<Integer> skuIds);

    /**
     * 锁定库存
     * @param order
     * @return
     */
    LockStockDto lockStock(Order order);
}
