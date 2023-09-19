package com.lzb.adapter.rpc.inverntory;

import java.util.List;

import com.lzb.adapter.rpc.inverntory.dto.LockStockReqDto;
import com.lzb.adapter.rpc.inverntory.dto.LockStockDetailReqDto;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRspDto;
import com.lzb.adapter.rpc.inverntory.dto.LockStockDetailRspDto;

import org.springframework.stereotype.Component;

/*@FeignClient(value = "inventory", contextId = "inventory",
        url = "",
        path = "",
        configuration = {FeignConfig.class, InnerGatewayFeignConfig.class})*/
@Component
public class InventoryClient {

    public LockStockRspDto lockStock(LockStockReqDto lockStockReqDto) {
        List<LockStockDetailReqDto> details = lockStockReqDto.getDetails();
        return new LockStockRspDto(details.stream().map(detail -> new LockStockDetailRspDto(detail.getSkuId(), 1)).toList());
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