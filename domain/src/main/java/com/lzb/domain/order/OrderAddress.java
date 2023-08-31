package com.lzb.domain.order;

import com.lzb.domain.common.BaseEntity;
import com.lzb.domain.order.valobj.FullAddressLine;
import com.lzb.domain.order.valobj.FullName;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 订单地址实体<br/>
 * Created on : 2023-08-31 12:52
 * @author lizebin
 */
@Getter
@SuperBuilder
public class OrderAddress extends BaseEntity<OrderAddress> {

    private Long orderId;

    private FullName fullName;

    private FullAddressLine fullAddressLine;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phoneNumber;

    /**
     * 国家
     */
    private String country;

}
