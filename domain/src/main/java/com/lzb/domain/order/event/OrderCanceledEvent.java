package com.lzb.domain.order.event;

import com.lzb.domain.common.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * 订单取消事件<br/>
 * Created on : 2023-09-02 20:15
 * @author lizebin
 */
@Getter
public class OrderCanceledEvent extends DomainEvent {

    private final long orderId;

    private OrderCanceledEvent(@NonNull String msgId,
            @NonNull String shardingKey,
            @NonNull String bizId, long orderId) {
        super(msgId, shardingKey, bizId);
        this.orderId = orderId;
    }

    public static OrderCanceledEvent create(@NonNull String msgId,
            @NonNull String shardingKey,
            @NonNull String bizId, long orderId) {
        return new OrderCanceledEvent(msgId, shardingKey, bizId, orderId);
    }


    @Override
    public String getTag() {
        return "ORDER_CANCELED";
    }

}
