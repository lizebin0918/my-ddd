import java.beans.ConstructorProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.common.aggregate.BaseEntity;
import com.lzb.domain.order.aggregate.Order;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-09-17 21:54
 * @author mac
 */
public class MyTest {

    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"lzb\",\"addTime\":123}";
        A order = JsonUtils.json2JavaBean(json, A.class);
        System.out.println(JsonUtils.toJSONString(order));
        String orderJson = "{\n"
                + "  \"currency\" : \"CNY\",\n"
                + "  \"exchangeRate\" : 1,\n"
                + "  \"orderAddress\" : {\n"
                + "    \"country\" : \"country\",\n"
                + "    \"email\" : \"email\",\n"
                + "    \"fullAddressLine\" : {\n"
                + "      \"addressLine1\" : \"addressLine1\",\n"
                + "      \"addressLine2\" : \"addressLine2\"\n"
                + "    },\n"
                + "    \"fullName\" : {\n"
                + "      \"firstName\" : \"firstName\",\n"
                + "      \"lastName\" : \"lastName\"\n"
                + "    },\n"
                + "    \"phoneNumber\" : \"phoneNumber\",\n"
                + "    \"version\" : 1\n"
                + "  },\n"
                + "  \"orderDetails\" : {\n"
                + "    \"list\" : [ {\n"
                + "      \"locked\" : true,\n"
                + "      \"orderId\" : 1,\n"
                + "      \"orderStatus\" : \"WAIT_PAY\",\n"
                + "      \"price\" : 1,\n"
                + "      \"skuId\" : 1,\n"
                + "      \"version\" : 1\n"
                + "    } ]\n"
                + "  },\n"
                + "  \"orderStatus\" : \"WAIT_PAY\",\n"
                + "  \"totalActualPay\" : 1,\n"
                + "  \"totalShouldPay\" : 1,\n"
                + "  \"version\" : 0\n"
                + "}";
        Order o = JsonUtils.json2JavaBean(orderJson, Order.class);
        System.out.println(JsonUtils.toJSONString(o));
    }

    @Getter
    public static class A extends BaseEntity<A> {
        String name;

        //@JsonCreator
        @ConstructorProperties({"id", "name"})
        public A(/*@JsonProperty("id")*/ long id, /*@JsonProperty("name")*/ String name) {
            super(id);
            this.name = name;
        }

        /*public A(long id, String name) {
            super(id);
            this.name = name;
        }*/
    }

}
