package com.lzb.infr.order.query;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.aggregation.valobj.OnSaleSku;
import com.lzb.domain.order.aggregation.valobj.Sku;
import com.lzb.domain.order.service.query.OnSaleQueryService;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2024-01-05 13:46
 * @author lizebin
 */
@Component
public class OnSaleQueryServiceDb implements OnSaleQueryService {

    @Override
    public List<OnSaleSku> onSale(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new OnSaleSku(new Sku(skuId), true)).toList();
    }
}
