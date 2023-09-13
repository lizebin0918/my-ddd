package com.lzb.domain.order.gateway;

import java.util.List;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.dto.LockStockResult;
import com.lzb.domain.order.dto.Sku;

/**
 * <br/>
 * Created on : 2023-09-08 09:06
 * @author mac
 */
public interface ProductGateway {

    /**
     * 查询上架的sku
     * @param skuIds
     * @return
     */
    List<Sku> onSale(int... skuIds);

    /**
     * 锁定库存
     * @param order
     * @return
     */
    LockStockResult lockStock(Order order);
}
