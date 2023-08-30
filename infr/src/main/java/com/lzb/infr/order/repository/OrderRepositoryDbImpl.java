package com.lzb.infr.order.repository;

import java.util.Optional;
import java.util.function.LongSupplier;

import com.lzb.domain.common.BaseRepository;
import com.lzb.domain.order.Order;
import com.lzb.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-08-30 23:08
 * @author mac
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryDbImpl extends BaseRepository<Order> implements OrderRepository {

    @Override
    protected LongSupplier doAdd(Order aggregate) {
        return null;
    }

    @Override
    protected Runnable doUpdate(Order aggregate) {
        return null;
    }

    @Override
    public Optional<Order> get(long id) {
        return Optional.empty();
    }
}
