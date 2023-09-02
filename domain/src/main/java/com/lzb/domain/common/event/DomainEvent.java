package com.lzb.domain.common.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public abstract class DomainEvent implements Serializable {

    private final long timestamp = System.currentTimeMillis();

    @NonNull
    private String msgId;

    @NonNull
    private String shardingKey;

    /**
     * 业务唯一id, 可用于做幂等
     */
    @NonNull
    private String bizId;

    public abstract String getTag();

}