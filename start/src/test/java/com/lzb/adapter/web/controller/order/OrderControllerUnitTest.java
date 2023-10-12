package com.lzb.adapter.web.controller.order;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.lzb.BaseControllerUnitTest;
import com.lzb.adapter.web.controller.test.TestOrderController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * <br/>
 * Created on : 2023-10-12 10:20
 * @author lizebin
 */
@WebMvcTest({OrderController.class})
class OrderControllerUnitTest extends BaseControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试")
    void should_() {
        System.out.println("order controller test");
    }

}
