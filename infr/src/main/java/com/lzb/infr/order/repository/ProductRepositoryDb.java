package com.lzb.infr.order.repository;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.repository.ProductRepository;
import com.lzb.domain.order.valobj.OnSaleSku;
import com.lzb.domain.order.valobj.Sku;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-12-31 16:56
 * @author lizebin
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryDb implements ProductRepository {

    @Override
    public List<OnSaleSku> onSale(Set<Integer> skuIds) {
        return skuIds.stream().map(skuId -> new OnSaleSku(new Sku(skuId), true)).toList();
    }
}
