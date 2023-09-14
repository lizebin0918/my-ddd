package com.lzb.component.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * <br/>
 * Created on : 2023-09-14 21:37
 * @author mac
 */
@Slf4j
public abstract class EventHandler<T> {


    protected EventHandler(EventBus eventBus) {
        log.info("regist eventbus {}", this.getClass());
        eventBus.register(this);
    }

    @Subscribe
    public abstract void handle(T t);

}
