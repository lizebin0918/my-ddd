package com.lzb.component.aggregate;


import java.io.Serializable;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 实体基类
 * @author mac
 * @date 2022/11/14
 */
@Slf4j
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseEntity<R extends BaseEntity<R>> implements Serializable {

    /**
     * id 新增的时候，可能为空
     */
    protected Long id;

    /**
     * 版本号，乐观锁使用,若数据库没有此字段，可忽略此字段
     */
    protected Integer version;

    public void setVersion(Integer version) {
        this.version = version;
    }

}
