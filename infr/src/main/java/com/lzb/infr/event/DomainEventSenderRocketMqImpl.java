package com.lzb.infr.event;

import com.lzb.component.utils.JsonUtils;
import com.lzb.domain.common.event.DomainEvent;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-01 10:17
 * @author lizebin
 */
@Service
public class DomainEventSenderRocketMqImpl implements DomainEventSender {

    @Override
    public void send(DomainEvent event) {
        System.out.println("发送领域事件：" + JsonUtils.toJSONString(event));
    }
}
