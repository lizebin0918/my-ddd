package com.lzb.domain.order.enums;

import com.lzb.component.utils.enums.EnumIntValue;
import com.lzb.component.utils.enums.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态<br/>
 * Created on : 2023-09-04 10:00
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public enum OrderStatus implements EnumValue<Integer> {

    WAIT_PAY(0, "待支付"), PAID(1, "已支付"), CANCELED(2, "已取消");

    private final Integer value;
    private final String desc;

}