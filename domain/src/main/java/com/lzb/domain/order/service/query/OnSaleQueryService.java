package com.lzb.domain.order.service.query;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.aggregation.valobj.OnSaleSku;
import jakarta.validation.constraints.NotEmpty;

/**
 * <br/>
 * Created on : 2024-01-05 13:45
 * @author lizebin
 */
public interface OnSaleQueryService {

    /**
     * 查询上架的sku状态
     * @param skuIds
     * @return
     */
    List<OnSaleSku> onSale(@NotEmpty Set<Integer> skuIds);

}
