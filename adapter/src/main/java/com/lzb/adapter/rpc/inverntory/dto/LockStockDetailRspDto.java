package com.lzb.adapter.rpc.inverntory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-09-13 12:54
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class LockStockDetailRspDto {

    private final int skuId;
    private final int lockedNum;

}
