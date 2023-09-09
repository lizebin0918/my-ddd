package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.BaseIntegrationTest;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.helper.SpringHelper;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.enums.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-08 13:33
 * @author lizebin
 */
class OrderBuilderIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private SpringHelper springHelper;

    @Autowired
    private IdGenerator idGenerator;

    @Test
    @DisplayName("测试Builder多实例")
    void should_new_multiple_instance() {
        OrderBuilder orderBuilder1 = OrderBuilder.newInstance();
        OrderBuilder orderBuilder2 = OrderBuilder.newInstance();
        assertThat(orderBuilder1).isNotSameAs(orderBuilder2);
    }

}
