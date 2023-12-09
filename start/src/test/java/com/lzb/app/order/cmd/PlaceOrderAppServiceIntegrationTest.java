package com.lzb.app.order.cmd;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.lzb.BaseIntegrationTest;
import com.lzb.adapter.rpc.inverntory.InventoryClient;
import com.lzb.adapter.rpc.inverntory.dto.LockStockDetailRspDto;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRspDto;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailDto;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.repository.OrderRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-09 14:23
 * @author lizebin
 */
class PlaceOrderAppServiceIntegrationTest extends BaseIntegrationTest {
    
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private PlaceOrderAppService placeOrderAppService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Test
    @DisplayName("测试OrderDetailBuilder")
    void should_place_order() {

        doReturn(1L).when(idGenerator).id();
        doReturn(new LockStockRspDto(Arrays.asList(new LockStockDetailRspDto(1, 1)))).when(inventoryClient).lockStock(any());

        PlaceOrderDto req = createPlaceOrderDto();

        long orderId = placeOrderAppService.placeOrder(req);
        Order order = orderRepository.getOrThrow(orderId);
        assertJSON(order, "id");
    }

    @NotNull
    private static PlaceOrderDto createPlaceOrderDto() {
        PlaceOrderDto req = new PlaceOrderDto("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailDto(1, BigDecimal.ONE)));
        return req;
    }

    @Test
    @DisplayName("测试缓存查询")
    void should_place_order_in_cache() {

        long orderId = 1L;
        doReturn(orderId).when(idGenerator).id();
        doReturn(new LockStockRspDto(Arrays.asList(new LockStockDetailRspDto(1, 1)))).when(inventoryClient).lockStock(any());

        assertThat(orderRepository.getInCache(orderId).isEmpty()).isTrue();
        placeOrderAppService.placeOrder(createPlaceOrderDto());
        assertThat(orderRepository.getInCache(orderId).isPresent()).isTrue();
    }

    @Test
    @DisplayName("测试日志组件是否生效")
    void should_test_log() {
        placeOrderAppService.testLog();
    }

}