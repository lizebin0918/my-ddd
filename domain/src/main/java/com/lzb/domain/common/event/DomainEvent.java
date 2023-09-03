package com.lzb.domain.common.event;

import java.io.Serializable;
import java.time.Instant;

import cn.hutool.core.lang.UUID;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class DomainEvent implements Serializable {

    private final long timestamp = Instant.now().toEpochMilli();

    private final String msgId = UUID.randomUUID().toString();

    private final String key;

    /**
     * 业务唯一id, 可用于做幂等
     */
    private final String bizId;

    protected DomainEvent(@NonNull String key, @NonNull String bizId) {
        this.key = key;
        this.bizId = bizId;
    }

    public abstract String getTag();

}