package com.lzb.app.order.query.view;

import com.lzb.domain.common.valobj.FullName;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import net.minidev.json.annotate.JsonIgnore;

/**
 * 订单View对象<br/>
 * Created on : 2023-09-06 22:44
 * @author mac
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class OrderView {

    @JsonIgnore
    private Order order;

    private Long orderId;

    private OrderStatus orderStatus;

    private FullName fullName;

    private String country;

    private String email;

    /**
     * 订单明细数量
     */
    private int detailCount;

    /**
     * 是否能取消
     */
    private boolean canCancel;

    public static OrderViewBuilder builder() {
        return new OrderViewBuilder();
    }

}
