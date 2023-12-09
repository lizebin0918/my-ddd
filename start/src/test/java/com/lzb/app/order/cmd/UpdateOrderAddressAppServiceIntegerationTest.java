package com.lzb.app.order.cmd;

import com.lzb.BaseIntegrationTest;
import com.lzb.app.order.cmd.dto.UpdateAddressDto;
import com.lzb.app.order.cmd.dto.UpdateFullNameDto;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.repository.OrderRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.jdbc.Sql;

class UpdateOrderAddressAppServiceIntegerationTest extends BaseIntegrationTest {

    @Resource
    private UpdateOrderAddressAppService updateOrderAddressAppService;

    @Resource
    private OrderRepository orderRepository;

    @Test
    @DisplayName("测试订单地址修改")
    @Sql("/sql/UpdateOrderAddressAppServiceIntegerationTest/should_update_order_address.sql")
    void should_update_order_address() {
        long orderId = 1L;
        UpdateAddressDto updateAddressDto = new UpdateAddressDto(orderId, "email1", "phoneNumber1", "firstName1",
                "lastName1", "addressLine11", "addressLine21", "country1");
        updateOrderAddressAppService.updateAddress(updateAddressDto);

        Order order = orderRepository.getOrThrow(orderId);
        assertJSON(order.getOrderAddress());
    }

    @Test
    @DisplayName("测试姓名修改")
    @Sql("/sql/UpdateOrderAddressAppServiceIntegerationTest/should_update_full_name.sql")
    void should_update_full_name() {
        long orderId = 1L;

        UpdateFullNameDto cmd = new UpdateFullNameDto(orderId, "li", "");
        updateOrderAddressAppService.updateFullName(cmd);

        Order order = orderRepository.getOrThrow(orderId);
        assertJSON(order.getOrderAddress());
    }

}