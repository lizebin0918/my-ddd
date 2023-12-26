package com.lzb.infr.stock.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 库存锁定请求<br/>
 * Created on : 2023-09-13 10:29
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class LockStockDetailReqDto {

    private final int skuId;
    private final int num;

}
