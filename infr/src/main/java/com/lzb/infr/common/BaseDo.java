package com.lzb.infr.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * <br/>
 * Created on : 2023-08-30 08:28
 * @author mac
 */
@Data
@FieldNameConstants
public abstract class BaseDo implements Serializable {

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime addTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
