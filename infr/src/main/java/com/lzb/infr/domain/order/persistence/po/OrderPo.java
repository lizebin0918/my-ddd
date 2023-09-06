package com.lzb.infr.domain.order.persistence.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.lzb.domain.order.enums.OrderStatus;
import com.lzb.infr.common.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javers.core.metamodel.annotation.Id;

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

    @Id
    @TableId(type = IdType.AUTO)
    private Long orderId;

    private OrderStatus orderStatus;

    private String currency;

    /**
     * 汇率
     */
    private BigDecimal exchangeRate;

    private BigDecimal totalShouldPay;

    private BigDecimal totalActualPay;

    @Version
    private int version;

    ///////////////////////////////////////////////////////////////////////////
    // 地址相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phoneNumber;

    private String firstName;

    private String lastName;

    /**
     * 地址1
     */
    private String addressLine1;

    /**
     * 地址2
     */
    private String addressLine2;

    /**
     * 国家
     */
    private String country;
}