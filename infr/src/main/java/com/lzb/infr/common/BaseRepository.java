package com.lzb.infr.common;


import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongSupplier;

import javax.annotation.Resource;

import com.lzb.domain.common.BaseAggregate;
import com.lzb.domain.common.repository.CommonRepository;
import com.lzb.infr.event.DomainEventSender;
import com.lzb.infr.transaction.TransactionHelper;
import lombok.NonNull;
import lombok.Setter;

@Setter
public abstract class BaseRepository<R extends BaseAggregate<R>> implements CommonRepository<R> {

    @Resource
    public TransactionHelper transactionHelper;

    @Resource
    public DomainEventSender domainEventSender;


    /**
     * 添加聚合根
     *
     * @param aggregate
     * @return 返回主键
     */
    protected abstract LongSupplier doAdd(R aggregate);

    /**
     * 更新聚合根
     *
     * @param aggregate
     * @return
     */
    protected abstract Runnable doUpdate(R aggregate);

    @Override
    public /*final*/ long add(@NonNull R aggregate) {
        AtomicReference<Long> id = new AtomicReference<>();
        LongSupplier idSupplier = doAdd(aggregate);
        transactionHelper.runWithRequired(() -> {
            id.set(idSupplier.getAsLong());
            domainEventSender.sendEvents(aggregate.getEvents());
        });
        return id.get();
    }

    @Override
    public /*final*/ void update(@NonNull R aggregate) {
        // 这样可读性会好点
        Runnable doUpdate = doUpdate(aggregate);
        transactionHelper.runWithRequired(() -> {
            doUpdate.run();
            domainEventSender.sendEvents(aggregate.getEvents());
        });
    }
}