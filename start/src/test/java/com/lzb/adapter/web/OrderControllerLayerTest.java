package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzb.adapter.config.JacksonConfig;
import com.lzb.app.order.cmd.PlaceOrderService;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.utils.json.JsonUtils;
import org.approvaltests.Approvals;
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
@Import(JacksonConfig.class)
class OrderControllerLayerTest {

    @MockBean
    private PlaceOrderService placeOrderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled("jackson还不支持jdk17")
    void placeOrder() throws Exception {
        PlaceOrderReq req = new PlaceOrderReq("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailReq(1, BigDecimal.ONE)));

        when(placeOrderService.placeOrder(any())).thenReturn(1L);

        ResultActions perform = mockMvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJSONString(req)));
        perform.andExpect(status().is(200));
        MvcResult result = perform.andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
    }

    @Test
    @DisplayName("测试提交参数和返回值")
    void should_test_req_rsp() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", "orderNo");
        params.put("status", "WAIT");
        params.put("amount", BigDecimal.ONE);
        params.put("skuCodes", List.of(1,2,3));

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/order/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJSONString(params));
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        JsonApprovals.verifyJson(contentAsString);
    }
}