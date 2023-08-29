package com.lzb.infr.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizebin
 * @since 2023-08-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("\"order\"")
public class Order implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long orderId;

    private String orderStatus;

    private String currency;

    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

    //@TableField(fill = FieldFill.INSERT)
    private LocalDateTime addTime;

    //@TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}