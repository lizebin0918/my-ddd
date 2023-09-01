package com.lzb.infr.order.persistence.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.infr.common.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javers.core.metamodel.annotation.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_detail")
public class OrderDetailPo extends BasePo {

    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Integer skuId;

    private String orderStatus;

    private BigDecimal price;


}
