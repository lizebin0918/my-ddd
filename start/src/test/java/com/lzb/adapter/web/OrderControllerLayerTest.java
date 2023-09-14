package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzb.adapter.web.order.OrderController;
import com.lzb.adapter.web.test.TestOrderController;
import com.lzb.app.order.cmd.PlaceOrderAppService;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.utils.json.JsonUtils;
import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest(TestOrderController.class)
class OrderControllerLayerTest {

    @MockBean
    private PlaceOrderAppService placeOrderAppService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试提交参数和返回值")
    void should_test_req_rsp() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", "orderNo");
        params.put("status", "WAIT");
        params.put("amount", BigDecimal.ONE);
        params.put("skuCodes", List.of(1,2,3));
        params.put("a", "a");

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/test")
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