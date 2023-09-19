package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.BaseUnitTest;
import com.lzb.domain.order.enums.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderDetailsUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("订单明细id重复，构造失败")
    void should_throw_exception_when_order_detail_id_is_duplicated() {

        OrderDetail orderDetail = new OrderDetail(1L, 1, OrderStatus.CANCELED, BigDecimal.ZERO, null);
        OrderDetail orderDetail1 = new OrderDetail(1L, 1,  OrderStatus.CANCELED, BigDecimal.ZERO, null);
        var list = List.of(orderDetail, orderDetail1);

        assertThrows(IllegalArgumentException.class, () -> new OrderDetails(list));

    }
  
}