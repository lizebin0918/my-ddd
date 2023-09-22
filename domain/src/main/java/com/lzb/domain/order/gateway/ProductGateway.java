package com.lzb.domain.order.gateway;

import java.util.List;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockDto;
import com.lzb.domain.order.dto.SkuDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

/**
 * <br/>
 * Created on : 2023-09-08 09:06
 * @author mac
 */
@Validated
public interface ProductGateway {

    /**
     * 查询上架的sku
     * @param skuIds
     * @return
     */
    List<SkuDto> onSale(int... skuIds);

    /**
     * 测试非空
     * @param skuIds
     * @return
     */
    List<SkuDto> onSale(@NotEmpty List<Integer> skuIds);

    /**
     * 锁定库存
     * @param order
     * @return
     */
    LockStockDto lockStock(Order order);
}
