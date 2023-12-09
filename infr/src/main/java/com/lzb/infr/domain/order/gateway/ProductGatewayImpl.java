package com.lzb.infr.domain.order.gateway;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.lzb.domain.order.dto.SkuInfoDto;
import com.lzb.domain.order.dto.SkuOnSaleDto;
import com.lzb.domain.order.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    @Override
    public List<SkuOnSaleDto> onSale(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuOnSaleDto(skuId, true)).toList();
    }

    @Override
    public List<SkuInfoDto> list(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new SkuInfoDto(skuId, "name-" + skuId, "picUrl-" + skuId)).toList();
    }

    @Override
    public List<SkuOnSaleDto> onSale(List<Integer> skuIds) {
        return Collections.emptyList();
    }

}
