package com.lzb.infr.event.sender;

import com.google.common.eventbus.EventBus;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.common.event.DomainEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-14 21:46
 * @author mac
 */
@Slf4j
@Service
public class DomainEventSenderEventBusImpl implements DomainEventSender {

    public static final String BEAN_NAME = "domainEventSenderEventBusImpl";

    @Resource
    private EventBus eventBus;

    @Override
    public void send(DomainEvent event) {
        log.info("发送领域事件 {}", JsonUtils.toJSONString(event));
        eventBus.post(event);
    }
}
