package com.lzb.infr.order.gateway;

import java.util.List;
import java.util.Set;

import com.lzb.app.order.query.quuery.ProductQueryService;
import com.lzb.domain.order.aggregation.valobj.Sku;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-12-31 17:01
 * @author lizebin
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class ProductQueryServiceDb implements ProductQueryService {

    @Override
    public List<Sku> list(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new Sku(skuId, "name-" + skuId, "picUrl-" + skuId)).toList();
    }
}
