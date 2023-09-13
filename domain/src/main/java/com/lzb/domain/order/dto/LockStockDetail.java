package com.lzb.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-09-12 23:22
 * @author mac
 */
@Getter
@AllArgsConstructor
public class LockStockDetail {

    private final long orderDetailId;
    private final boolean locked;

}
