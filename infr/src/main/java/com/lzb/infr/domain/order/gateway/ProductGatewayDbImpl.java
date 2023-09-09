package com.lzb.infr.domain.order.gateway;

import java.util.List;
import java.util.stream.IntStream;

import com.lzb.domain.order.dto.Sku;
import com.lzb.domain.order.gateway.ProductGateway;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-09 20:27
 * @author lizebin
 */
@Component(ProductGatewayDbImpl.BEAN_NAME)
public class ProductGatewayDbImpl implements ProductGateway {

    public static final String BEAN_NAME = "productGatewayDbImpl";

    @Override
    public List<Sku> onSale(int... skuIds) {
        return IntStream.of(skuIds).mapToObj(skuId -> new Sku(skuId, true)).toList();
    }
}
