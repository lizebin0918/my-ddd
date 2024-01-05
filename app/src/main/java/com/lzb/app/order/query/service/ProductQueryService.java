package com.lzb.app.order.query.service;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.aggregation.valobj.Sku;

public interface ProductQueryService {

    /**
     * 查询sku信息
     *
     * @param skuIds
     * @return
     */
    List<Sku> list(Set<Integer> skuIds);

}