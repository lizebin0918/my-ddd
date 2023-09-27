package com.lzb.app.order.cmd.eventhanlder;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.lzb.component.eventbus.EventHandler;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.order.event.OrderCanceledEvent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-08 09:06
 * @author mac
 */
@Slf4j
@Lazy(false)
@Component
public class OrderCanceledEventHandler extends EventHandler<OrderCanceledEvent> {

    public OrderCanceledEventHandler(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    @Subscribe
    public void handle(OrderCanceledEvent event) {
        log.info("收到订单事件:" + JsonUtils.toJSONString(event));
    }

}