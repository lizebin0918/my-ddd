package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.domain.order.dto.PlaceOrderCmd;
import com.lzb.domain.order.dto.PlaceOrderDetailCmd;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class OrderControllerE2E {

    @Test
    @Disabled("事务无法回滚，会起一个新的线程")
    void placeOrder() {
        PlaceOrderCmd req = new PlaceOrderCmd("CNY", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, "email",
                "phoneNumber", "firstName", "lastName", "addressLine1", "addressLine2", "country",
                List.of(new PlaceOrderDetailCmd(1, BigDecimal.ONE)));
        //restTemplate.put("http://localhost:" + port + "/order", req);
    }
}