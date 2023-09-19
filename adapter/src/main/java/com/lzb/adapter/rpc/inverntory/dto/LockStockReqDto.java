package com.lzb.adapter.rpc.inverntory.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存锁定请求<br/>
 * Created on : 2023-09-13 10:29
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class LockStockReqDto {

    private final String channelOrderId;
    private final List<LockStockDetailReqDto> details;

}
