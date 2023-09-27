package com.lzb.domain.order.aggregate;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.lzb.BaseUnitTest;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.order.enums.OrderStatus;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-09-18 19:50
 * @author lizebin
 */
class OrderUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("测试序列化和反序列化")
    void should_json() {
        String orderJson = """
                {
                    "id":1,
                  "currency" : "CNY",
                  "exchangeRate" : 1,
                  "orderAddress" : {
                    "country" : "country",
                    "email" : "email",
                    "fullAddressLine" : {
                      "addressLine1" : "addressLine1",
                      "addressLine2" : "addressLine2"
                    },
                    "fullName" : {
                      "firstName" : "firstName",
                      "lastName" : "lastName"
                    },
                    "phoneNumber" : "phoneNumber",
                    "version" : 1
                  },
                  "orderDetails" : {
                    "list" : [ {
                      "locked" : true,
                      "orderId" : 1,
                      "orderStatus" : "WAIT_PAY",
                      "price" : 1,
                      "skuId" : 1,
                      "version" : 1,
                      "id": 1
                    } ]
                  },
                  "orderStatus" : "WAIT_PAY",
                  "totalActualPay" : 1,
                  "totalShouldPay" : 1,
                  "version" : 0
                }
                                """;
        Order o = JsonUtils.json2JavaBean(orderJson, Order.class);
        assertJSON(o);
    }

    @Test
    @DisplayName("测试手动setter")
    void should_setter() {
        Order o = new Order(1L);
        o.setCurrency("CNY");
        o.setExchangeRate(BigDecimal.ONE);
        o.setOrderStatus(OrderStatus.WAIT_PAY);
        o.setTotalActualPay(BigDecimal.ONE);
        o.setTotalShouldPay(BigDecimal.ONE);
        o.setOrderAddress(new OrderAddress(1L));
        o.setOrderDetails(new OrderDetails(List.of(new OrderDetail(1L))));
        assertJSON(o);
    }

    @Test
    @DisplayName("通过构造函数反序列化")
    void should_json_constructor() {
        String json = "{\"id\":1}";
        A a = JsonUtils.json2JavaBean(json, A.class);
        assertThat(a.getId()).isEqualTo(1);
    }

    @Getter
    public static class A {
        private final int id;
        private final String name;

        @JsonCreator
        public A(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

}
