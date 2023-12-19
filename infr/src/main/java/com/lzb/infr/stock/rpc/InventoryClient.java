package com.lzb.infr.stock.rpc;

import java.util.List;

import com.lzb.infr.stock.rpc.dto.LockStockDetailReqDto;
import com.lzb.infr.stock.rpc.dto.LockStockDetailRspDto;
import com.lzb.infr.stock.rpc.dto.LockStockReqDto;
import com.lzb.infr.stock.rpc.dto.LockStockRspDto;

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