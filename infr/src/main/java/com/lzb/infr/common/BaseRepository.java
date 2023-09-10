package com.lzb.infr.common;


import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongSupplier;

import com.lzb.component.helper.TransactionHelper;
import com.lzb.domain.common.BaseAggregate;
import com.lzb.domain.common.repository.CommonRepository;
import com.lzb.infr.event.DomainEventSupport;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.aop.framework.AopContext;

@Setter
public abstract class BaseRepository<R extends BaseAggregate<R>> implements CommonRepository<R> {

    @Resource
    protected TransactionHelper transactionHelper;

    @Resource
    protected DomainEventSupport domainEventSupport;

    /**
     * 添加聚合根
     *
     * @param aggregate
     * @return 返回主键
     */
    public abstract LongSupplier doAdd(R aggregate);

    /**
     * 更新聚合根
     *
     * @param aggregate
     * @return
     */
    public abstract Runnable doUpdate(R aggregate);

    private BaseRepository<R> getCurrentProxy() {
        return (BaseRepository<R>) AopContext.currentProxy();
    }

    @Override
    public /*final 无法切面*/ long add(@NonNull R aggregate) {
        AtomicReference<Long> id = new AtomicReference<>();
        LongSupplier idSupplier = getCurrentProxy().doAdd(aggregate);
        transactionHelper.runWithRequired(() -> {
            id.set(idSupplier.getAsLong());
            domainEventSupport.asynSendAfterPersist(aggregate.getEvents());
        });
        return id.get();
    }


    @Override
    public /*final 无法切面*/ void update(@NonNull R aggregate) {
        // 这样可读性会好点
        Runnable doUpdate = getCurrentProxy().doUpdate(aggregate);
        transactionHelper.runWithRequired(() -> {
            doUpdate.run();
            domainEventSupport.asynSendAfterPersist(aggregate.getEvents());
        });
    }
}