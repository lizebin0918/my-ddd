package com.lzb.domain.order.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;

/**
 * <br/>
 * Created on : 2023-09-12 23:20
 * @author mac
 */
@AllArgsConstructor
public class LockStockResult {

    private final List<LockStockDetail> details = Collections.emptyList();

    public Optional<LockStockDetail> getBy(long orderDetailId) {
        return StreamEx.of(details).findAny(item -> Objects.equals(orderDetailId, item.getOrderDetailId()));
    }

}
