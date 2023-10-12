package com.lzb.adapter.web.controller.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzb.BaseControllerUnitTest;
import com.lzb.adapter.web.controller.test.Status;
import com.lzb.adapter.web.controller.test.TestOrder;
import com.lzb.adapter.web.controller.test.TestOrderController;
import com.lzb.app.order.cmd.PlaceOrderAppService;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.servlet.ServletException;
import org.approvaltests.JsonApprovals;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller分层测试<br/>
 * Created on : 2023-09-06 13:34
 * @Author lizebin
 */
class TestOrderControllerUnitTest extends BaseControllerUnitTest {

    @MockBean
    private PlaceOrderAppService placeOrderAppService;

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

    @Test
    @DisplayName("orderNo is blank")
    void should_exception_when_order_no_is_blank() throws Exception {
        TestOrder testOrder = new TestOrder("", BigDecimal.ONE, List.of("1", "2", "3"), Status.WAIT);
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJSONString(testOrder));
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("测试@Validated声明在Controller上，用于校验参数")
    void should_validate_controller() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/name")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("name", "1");

        assertThrows(ServletException.class, () -> mockMvc.perform(content));
    }
}