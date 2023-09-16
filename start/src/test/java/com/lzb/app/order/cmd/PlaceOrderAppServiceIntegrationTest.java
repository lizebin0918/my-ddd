package com.lzb.app.order.cmd;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.lzb.BaseIntegrationTest;
import com.lzb.adapter.rpc.inverntory.InventoryClient;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRsp;
import com.lzb.adapter.rpc.inverntory.dto.LockStockRspDetail;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailCmd;
import com.lzb.app.order.cmd.dto.PlaceOrderCmd;
import com.lzb.component.id.IdGenerator;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.repository.OrderRepository;
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
        doReturn(new LockStockRsp(Arrays.asList(new LockStockRspDetail(1, 1)))).when(inventoryClient).lockStock(any());

        PlaceOrderCmd req = new PlaceOrderCmd("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailCmd(1, BigDecimal.ONE)));

        long orderId = placeOrderAppService.placeOrder(req);
        Order order = orderRepository.getOrThrow(orderId);
        assertJSON(order, "id");
    }

}