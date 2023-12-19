package com.lzb.domain.order.gateway;

import java.util.List;
import java.util.Set;

import com.lzb.domain.order.dto.SkuInfoDto;
import com.lzb.domain.order.dto.SkuOnSaleDto;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

/**
 * <br/>
 * Created on : 2023-09-08 09:06
 * @author mac
 */
@Validated
public interface OrderProductGateway {

    /**
     * 查询上架的sku状态
     * @param skuIds
     * @return
     */
    List<SkuOnSaleDto> onSale(@NotEmpty Set<Integer> skuIds);

    /**
     * 查询sku信息
     *
     * @param skuIds
     * @return
     */
    List<SkuInfoDto> list(@NotEmpty Set<Integer> skuIds);

}
