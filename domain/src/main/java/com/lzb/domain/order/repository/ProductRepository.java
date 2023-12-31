package com.lzb.domain.order.repository;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.valobj.OnSaleSku;
import jakarta.validation.constraints.NotEmpty;

/**
 * <br/>
 * Created on : 2023-12-31 16:55
 * @author lizebin
 */
public interface ProductRepository {

    /**
     * 查询上架的sku状态
     * @param skuIds
     * @return
     */
    List<OnSaleSku> onSale(@NotEmpty Set<Integer> skuIds);

}
