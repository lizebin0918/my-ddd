package com.lzb.domain.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import one.util.streamex.StreamEx;

/**
 * <br/>
 * Created on : 2023-09-12 23:20
 * @author mac
 */
@Getter
@AllArgsConstructor
public class LockStockDto {

    @NonNull
    private final List<LockStockDetailDto> details;

    @Getter
    @AllArgsConstructor
    public static class LockStockDetailDto {

        /**
         * skuId
         */
        private final int skuId;
        /**
         * 锁定数量
         */
        private final int lockedNum;

    }

    public LockStockDetailDto getDetail(int skuId) {
        return StreamEx.of(details).findFirst(detail -> detail.getSkuId() == skuId).orElseThrow();
    }
}
