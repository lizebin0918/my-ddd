package com.lzb.domain.order.gateway;

import java.util.List;

import com.lzb.domain.order.dto.Sku;

/**
 * <br/>
 * Created on : 2023-09-08 09:06
 * @author mac
 */
public interface ProductGateway {

    /**
     * 查询上架的sku
     * @param skuId
     * @return
     */
    List<Sku> onSale(int... skuId);
}
