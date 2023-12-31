package com.lzb.component.domain.aggregate;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 实体基类
 * @author mac
 * @date 2022/11/14
 */
@Slf4j
@Getter
@AllArgsConstructor
@SuperBuilder
public abstract class BaseEntity<R extends BaseEntity<R>> implements Serializable {

    protected BaseEntity(Long id) {
        this.id = id;
        this.version = 1;
    }

    /**
     * id 新增的时候，可能为空
     */
    protected Long id;

    /**
     * 版本号，乐观锁使用,若数据库没有此字段，可忽略此字段
     */
    @Builder.Default
    protected int version = 1;

}
