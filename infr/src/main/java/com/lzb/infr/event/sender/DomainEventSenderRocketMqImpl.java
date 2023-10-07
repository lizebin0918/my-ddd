package com.lzb.infr.event.sender;

import java.util.function.Consumer;

import com.lzb.component.utils.json.JsonUtils;
import com.lzb.component.domain.event.DomainEvent;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-01 10:17
 * @author lizebin
 */
@Service(DomainEventSenderRocketMqImpl.BEAN_NAME)
public class DomainEventSenderRocketMqImpl implements DomainEventSender {

    public static final String BEAN_NAME = "domainEventSenderRocketMqImpl";

    @Override
    public void send(DomainEvent event, Consumer<DomainEvent> callback) {
        System.out.println("发送领域事件：" + JsonUtils.toJSONString(event));
    }
}
