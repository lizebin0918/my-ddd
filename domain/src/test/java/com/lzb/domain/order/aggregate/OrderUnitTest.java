package com.lzb.domain.order.aggregate;

import com.lzb.BaseUnitTest;
import com.lzb.component.utils.json.JsonUtils;
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

}
