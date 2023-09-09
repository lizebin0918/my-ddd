package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.BaseIntegrationTest;
import com.lzb.app.order.cmd.dto.PlaceOrderDetailReq;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    @Disabled
    void placeOrder() {
        PlaceOrderReq req = new PlaceOrderReq("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailReq(1, BigDecimal.ONE)));
        restTemplate.put("http://localhost:" + port + "/order", req);
    }
}