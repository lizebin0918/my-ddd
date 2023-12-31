package com.lzb.app.order.query.gateway;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.valobj.Sku;

public interface ProductGateway {

    /**
     * 查询sku信息
     *
     * @param skuIds
     * @return
     */
    List<Sku> list(Set<Integer> skuIds);

}