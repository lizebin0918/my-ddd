package com.lzb.adapter.rpc.inverntory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 库存锁定请求<br/>
 * Created on : 2023-09-13 10:29
 * @author lizebin
 */
@Getter
@RequiredArgsConstructor
public class LockStockReqDetail {

    private final int skuId;
    private final int num;

}
