package com.lzb.adapter.rpc.inverntory;

import java.util.List;

import com.lzb.adapter.rpc.inverntory.dto.LockStockReq;
import com.lzb.adapter.rpc.inverntory.dto.LockStockReqDetail;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRsp;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRspDetail;

import org.springframework.stereotype.Component;

/*@FeignClient(value = "inventory", contextId = "inventory",
        url = "",
        path = "",
        configuration = {FeignConfig.class, InnerGatewayFeignConfig.class})*/
@Component
public class InventoryClient {

    public LockStockRsp lockStock(LockStockReq lockStockReq) {
        List<LockStockReqDetail> details = lockStockReq.getDetails();
        return new LockStockRsp(details.stream().map(detail -> new LockStockRspDetail(detail.getSkuId(), 1)).toList());
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