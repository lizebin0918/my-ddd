package com.lzb.app.order.query;

import java.util.Set;

import com.lzb.BaseIntegrationTest;
import com.lzb.app.order.query.dto.OrderQueryDto;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.jdbc.Sql;

/**
 * <br/>
 * Created on : 2023-09-23 08:21
 * @author mac
 */
class OrderQueryAppServiceIntegrationTest extends BaseIntegrationTest {

    @Resource
    private OrderQueryAppService orderQueryAppService;

    @Test
    @DisplayName("列表测试")
    @Sql("/sql/OrderQueryAppServiceIntegrationTest/should_list_for_page.sql")
    void should_list_for_page() {
        OrderQueryDto query = new OrderQueryDto(Set.of(), "", 1, 10);
        assertJSON(orderQueryAppService.listForPage(query));
    }

}
