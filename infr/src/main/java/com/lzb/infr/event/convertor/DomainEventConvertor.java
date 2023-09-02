package com.lzb.infr.event.convertor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.lzb.component.utils.JsonUtils;
import com.lzb.domain.common.event.DomainEvent;
import com.lzb.infr.event.persistence.DomainEventPo;
import lombok.experimental.UtilityClass;

/**
 * <br/>
 * Created on : 2023-09-02 12:57
 * @author lizebin
 */
@UtilityClass
public final class DomainEventConvertor {

    public static DomainEventPo toDomainEventPo(String topic, DomainEvent event) {
        DomainEventPo domainEventPo = new DomainEventPo();
        domainEventPo.setTopic(topic);
        domainEventPo.setTag(event.getTag());
        domainEventPo.setBizId(event.getBizId());
        domainEventPo.setShardingKey(event.getShardingKey());
        domainEventPo.setContent(JsonUtils.toJSONString(event));
        domainEventPo.setMsgId(event.getMsgId());
        return domainEventPo;
    }

    public static List<DomainEventPo> toDomainEventPos(String topic, Collection<DomainEvent> events) {
        return events.stream().map(event -> toDomainEventPo(topic, event)).toList();
    }

}
