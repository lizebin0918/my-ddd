package com.lzb.domain.order.service;

import java.util.Set;

import com.lzb.component.exception.BizException;
import com.lzb.domain.order.repository.OrderProductRepository;
import com.lzb.domain.order.aggregation.valobj.OnSaleSku;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * sku校验<br/>
 * Created on : 2023-09-09 20:15
 * @author lizebin
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class SkuValidator {

    private final OrderProductRepository orderProductRepository;

    public void assertAllOfSkuIsOnSale(Set<Integer> skuIds) {
        boolean allIsOnSale = StreamEx.of(orderProductRepository.onSale(skuIds)).allMatch(OnSaleSku::isOnSale);
        if (!allIsOnSale) {
            throw new BizException("订单明细包含下架商品，无法创建订单");
        }
    }

}
