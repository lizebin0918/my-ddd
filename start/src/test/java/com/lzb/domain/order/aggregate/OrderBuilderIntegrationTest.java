package com.lzb.domain.order.aggregate;

import com.lzb.BaseIntegrationTest;
import com.lzb.component.helper.SpringHelper;
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

    @Test
    @DisplayName("测试Builder多实例")
    void should_new_multiple_instance() {
        OrderBuilder orderBuilder1 = OrderBuilder.newInstance();
        OrderBuilder orderBuilder2 = OrderBuilder.newInstance();
        assertThat(orderBuilder1).isNotSameAs(orderBuilder2);
        assertThat(orderBuilder1.id()).isNotEqualTo(orderBuilder2.id());
    }

}
