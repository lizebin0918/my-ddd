package com.lzb.infr.order.persistence.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.infr.common.BasePo;
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
public class OrderPo extends BasePo {

    @TableId(type = IdType.AUTO)
    private Long orderId;

    private String orderStatus;

    private String currency;

    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

}