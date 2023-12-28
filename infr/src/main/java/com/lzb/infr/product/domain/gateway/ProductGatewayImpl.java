package com.lzb.infr.product.domain.gateway;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.valobj.OnSaleSku;
import com.lzb.domain.order.valobj.Sku;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 外部定义接口<br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class ProductGatewayImpl implements
        com.lzb.domain.order.gateway.ProductGateway,
        com.lzb.domain.product.gateway.ProductGateway  {

    @Override
    public List<OnSaleSku> onSale(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new OnSaleSku(new Sku(skuId), true)).toList();
    }

    @Override
    public List<Sku> list(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new Sku(skuId, "name-" + skuId, "picUrl-" + skuId)).toList();
    }

}
