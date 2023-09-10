package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.app.order.cmd.PlaceOrderService;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.utils.json.JsonUtils;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller分层测试<br/>
 * Created on : 2023-09-06 13:34
 * @Author lizebin
 */
@WebMvcTest(OrderController.class)
class OrderControllerLayerTest {

    @MockBean
    private PlaceOrderService placeOrderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void placeOrder() throws Exception {
        PlaceOrderReq req = new PlaceOrderReq("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailReq(1, BigDecimal.ONE)));

        when(placeOrderService.placeOrder(any())).thenReturn(1L);

        mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJSONString(req)))
                .andExpect(status().is(200));
    }
}