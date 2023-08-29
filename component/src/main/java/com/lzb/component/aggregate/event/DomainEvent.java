package com.lzb.component.aggregate.event;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public abstract class DomainEvent implements Serializable {

    private final long timestamp = System.currentTimeMillis();

    /**
     * 业务唯一id, 可用于做幂等
     */
    private String bizUniqueId;

    protected DomainEvent() {}

}