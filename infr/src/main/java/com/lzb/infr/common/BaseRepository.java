package com.lzb.infr.common;


import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongSupplier;

import com.lzb.component.helper.TransactionHelper;
import com.lzb.component.domain.aggregate.BaseAggregate;
import com.lzb.component.domain.repository.CommonRepository;
import com.lzb.infr.event.DomainEventSupport;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.Setter;

import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Setter
public abstract class BaseRepository<R extends BaseAggregate<R>> implements CommonRepository<R>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    protected TransactionHelper transactionHelper;

    @Resource
    protected DomainEventSupport domainEventSupport;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
        if (Objects.isNull(applicationContext)) {
            return this;
        }
        return (BaseRepository<R>) AopContext.currentProxy();
    }

    @Override
    public /*final 无法切面*/ long add(@NonNull R aggregate) {
        AtomicReference<Long> id = new AtomicReference<>();
        LongSupplier idSupplier = getCurrentProxy().doAdd(aggregate);
        transactionHelper.runWithRequired(() -> {
            id.set(idSupplier.getAsLong());
            domainEventSupport.sendEventAfterPersist(aggregate.getEvents());
        });
        return id.get();
    }


    @Override
    public /*final 无法切面*/ void update(@NonNull R aggregate) {
        // 这样可读性会好点
        Runnable doUpdate = getCurrentProxy().doUpdate(aggregate);
        transactionHelper.runWithRequired(() -> {
            doUpdate.run();
            domainEventSupport.sendEventAfterPersist(aggregate.getEvents());
        });
    }
}