package com.lzb.domain.common.event;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class DomainEvent implements Serializable {

    private final long timestamp = System.currentTimeMillis();

    private String msgId;
    private String key;
    private String shardingKey;

    public abstract String getTag();

    /**
     * 业务唯一id, 可用于做幂等
     */
    private String bizId;

    protected DomainEvent() {}

}