package com.lzb.infr.order.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.infr.common.BaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_detail")
public class OrderDetailDo extends BaseDo {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Integer skuId;

    private String orderStatus;

    private BigDecimal price;


}
