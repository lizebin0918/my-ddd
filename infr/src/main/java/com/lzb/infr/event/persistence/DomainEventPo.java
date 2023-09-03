package com.lzb.infr.event.persistence;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.infr.common.BasePo;
import com.lzb.infr.event.typehandler.ContentTypeHandler;
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
@TableName("domain_event")
public class DomainEventPo extends BasePo {

    private String topic;

    private String tag;

    private String bizId;

    private String key;

    @TableField(typeHandler = ContentTypeHandler.class)
    private String content;

    private Boolean sent;

    private String msgId;

}