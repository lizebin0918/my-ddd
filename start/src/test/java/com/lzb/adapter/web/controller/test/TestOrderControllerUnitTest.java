package com.lzb.adapter.web.controller.test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lzb.BaseControllerUnitTest;
import com.lzb.app.order.cmd.PlaceOrderAppService;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
        params.put("skuCodes", List.of(1, 2, 3));
        params.put("a", "a");

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJSONString(params));
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());

        assertResponse(perform);
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

    @Test
    @DisplayName("测试多个参数")
    void should_testMultipleParamter() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String name = "1";
        params.put("name", List.of(name));
        String age = "18";
        params.put("age", List.of(age));
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/testMultipleParamter")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParams(params);

        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());

        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String contentAsString = response.getContentAsString();
        assertThat(contentAsString).isEqualTo(name + "|" + age);
    }

    @Test
    @DisplayName("测试提交参数和返回值")
    void should_test_req_rsp_1() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", "orderNo");
        params.put("status", "WAIT");
        params.put("amount", BigDecimal.ONE);
        params.put("skuCodes", List.of(1, 2, 3));

        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/test1")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("uid", "1")
                .queryParam("ids", "1,2,3")
                .content(JsonUtils.toJSONString(params));
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());

        assertResponse(perform);
    }

    @Test
    @DisplayName("测试传参")
    void should_test_req_rsp_2() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/test2")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("uid", "1")
                .queryParam("ids", "1,2,3")
                .queryParam("amount", "1.1")
                // String name 接收：spring 会自动帮你连起来
                .queryParam("name", "lzb", "lzb1")
                .queryParam("names", "lzb2", "lzb3");
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());

        assertResponse(perform);
    }

    @Test
    @DisplayName("测试多个对象的请求参数")
    void test_multiple_request_body() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("value", List.of("1"));
        params.put("unit", List.of("CM"));
        params.put("firstName", List.of("firstName"));
        params.put("lastName", List.of("lastName"));
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/testMultipleObjectParamter")
                .contentType(MediaType.APPLICATION_JSON)
                .params(params);
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());
        assertResponse(perform);
    }

    @Test
    @Disabled
    @DisplayName("测试睡眠，看响应时间")
    void test_sleep() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.get("/testSleep")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(content)
                .andExpect(status().is2xxSuccessful());
    }
}