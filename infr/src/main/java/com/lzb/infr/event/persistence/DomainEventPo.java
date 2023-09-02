package com.lzb.infr.event.persistence;

import com.lzb.infr.common.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizebin
 * @since 2023-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DomainEventPo extends BasePo {

    private String topic;

    private String tag;

    private String bizId;

    private String shardingKey;

    private String content;

    private Boolean sent;

    private String msgId;

}