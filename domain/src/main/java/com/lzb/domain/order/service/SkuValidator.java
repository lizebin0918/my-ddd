package com.lzb.domain.order.service;

import java.util.Set;

import com.lzb.component.exception.BizException;
import com.lzb.domain.order.dto.SkuOnSaleDto;
import com.lzb.domain.order.gateway.ProductGateway;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.stereotype.Component;

/**
 * sku校验<br/>
 * Created on : 2023-09-09 20:15
 * @author lizebin
 */
@Component
@RequiredArgsConstructor
public class SkuValidator {

    private final ProductGateway productGateway;

    public void assertAllOfSkuIsOnSale(Set<Integer> skuIds) {
        boolean allIsOnSale = StreamEx.of(productGateway.onSale(skuIds)).allMatch(SkuOnSaleDto::isOnSale);
        if (!allIsOnSale) {
            throw new BizException("订单明细包含下架商品，无法创建订单");
        }
    }

}
