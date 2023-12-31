package com.lzb.domain.product.aggregation;

import com.lzb.component.domain.aggregate.BaseAggregation;

/**
 * 商品聚合根<br/>
 * Created on : 2023-12-19 13:43
 * @author lizebin
 */
public class Product extends BaseAggregation<Product> {

    protected Product(long id) {
        super(id);
    }

}
