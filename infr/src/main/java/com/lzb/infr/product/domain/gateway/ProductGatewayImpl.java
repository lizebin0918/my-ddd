package com.lzb.infr.product.domain.gateway;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.dto.SkuInfoDto;
import com.lzb.domain.order.dto.SkuOnSaleDto;
import com.lzb.domain.order.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * 外部定义接口<br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements
        com.lzb.domain.order.gateway.ProductGateway,
        com.lzb.domain.product.gateway.ProductGateway  {

    @Override
    public List<SkuOnSaleDto> onSale(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuOnSaleDto(skuId, true)).toList();
    }

    @Override
    public List<SkuInfoDto> list(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuInfoDto(skuId, "name-" + skuId, "picUrl-" + skuId)).toList();
    }

}
