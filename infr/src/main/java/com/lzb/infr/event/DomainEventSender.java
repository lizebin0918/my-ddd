package com.lzb.infr.event;

import java.util.Queue;

import com.lzb.domain.common.event.DomainEvent;

/**
 * 领域事件发送<br/>
 * Created on : 2022-03-01 13:34
 *
 * @author lizebin
 */
public interface DomainEventSender {

    /**
     * 1.持久化消息，方便重试
     * 2.发送领域事件
     * @param event
     */
    void send(DomainEvent event);

    default void sendEvents(Queue<DomainEvent> events) {
        while (!events.isEmpty()) {
            send(events.poll());
        }
    }
}
