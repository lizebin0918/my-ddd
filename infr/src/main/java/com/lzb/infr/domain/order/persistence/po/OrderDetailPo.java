package com.lzb.infr.domain.order.persistence.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzb.domain.order.enums.OrderStatus;
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

    private OrderStatus orderStatus;

    private BigDecimal price;

    private Boolean locked;


}
