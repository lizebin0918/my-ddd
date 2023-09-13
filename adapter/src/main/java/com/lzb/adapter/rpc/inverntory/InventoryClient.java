package com.lzb.adapter.rpc.inverntory;

import com.lzb.adapter.rpc.inverntory.dto.LockStockReq;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRsp;

import org.springframework.stereotype.Component;

/*@FeignClient(value = "inventory", contextId = "inventory",
        url = "",
        path = "",
        configuration = {FeignConfig.class, InnerGatewayFeignConfig.class})*/
@Component
public class InventoryClient {

    public LockStockRsp lockStock(LockStockReq lockStockReq) {
        return null;
    }

    /**
     * 锁定库存
     */
    /*@PostMapping("/stock/v1/lockStock")
    void lockStock(StockLockReqDTO reqDTO);*/



    /**
     * 解锁库存
     */
    /*@PostMapping("/stock/v1/unlockStock")
    void unlockStock(StockOperateReqDTO reqDTO);*/
}