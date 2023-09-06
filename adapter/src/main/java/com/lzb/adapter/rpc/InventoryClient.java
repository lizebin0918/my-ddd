package com.lzb.adapter.rpc;

import org.springframework.web.bind.annotation.PostMapping;

/*@FeignClient(value = "inventory", contextId = "inventory",
        url = "",
        path = "",
        configuration = {FeignConfig.class, InnerGatewayFeignConfig.class})*/
public interface InventoryClient {

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