package com.lzb.adapter.rpc.inverntory.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存锁定结果<br/>
 * Created on : 2023-09-13 10:29
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class LockStockRsp {

    private List<LockStockRspDetail> lockedDetails;

}
