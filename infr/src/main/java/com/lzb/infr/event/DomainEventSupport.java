package com.lzb.infr.event;

import java.util.List;
import java.util.Queue;

import javax.annotation.Resource;

import com.lzb.domain.common.event.DomainEvent;
import com.lzb.infr.event.common.Constants;
import com.lzb.infr.event.convertor.DomainEventConvertor;
import com.lzb.infr.event.persistence.DomainEventPo;
import com.lzb.infr.event.persistence.service.DomainEventPoService;
import com.lzb.infr.event.sender.DomainEventSender;
import com.lzb.infr.event.sender.DomainEventSenderRocketMqImpl;
import com.lzb.infr.transaction.TransactionHelper;
import lombok.NonNull;

import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-09-02 13:44
 * @author lizebin
 */
@Component
public class DomainEventSupport {

    @Resource(name = DomainEventSenderRocketMqImpl.BEAN_NAME)
    public DomainEventSender domainEventSender;

    @Resource
    private DomainEventPoService domainEventPoService;

    @Resource
    private TransactionHelper transactionHelper;

    /**
     * 持久化之后，异步发送事件
     * @param events
     */
    public void asynSendAfterPersist(@NonNull Queue<DomainEvent> events) {
        transactionHelper.runWithRequired(() -> {
            List<DomainEventPo> domainEventPos = DomainEventConvertor.toDomainEventPos(Constants.TOPIC, events);
            boolean success = domainEventPoService.saveBatch(domainEventPos);
            if (success) {
                transactionHelper.runAfterCommit(() -> domainEventSender.sendEvents(events));
            }
        });
    }

}
