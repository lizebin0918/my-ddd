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
        A order = JsonUtils.INSTANCE.readerFor(A.class).readValue(json);
        System.out.println(JsonUtils.toJSONString(order));
        String orderJson = "{\n"
                + "    \"cancel\":false,\n"
                + "    \"currency\":\"CNY\",\n"
                + "    \"events\":null,\n"
                + "    \"exchangeRate\":1,\n"
                + "    \"id\":1,\n"
                + "    \"logs\":null,\n"
                + "    \"orderAddress\":{\n"
                + "        \"country\":\"CHINA\",\n"
                + "        \"email\":\"123456@qq.com\",\n"
                + "        \"fullAddressLine\":{\n"
                + "            \"addressLine1\":\"line1\",\n"
                + "            \"addressLine2\":\"line2\"\n"
                + "        },\n"
                + "        \"fullName\":{\n"
                + "            \"firstName\":\"first\",\n"
                + "            \"lastName\":\"last\"\n"
                + "        },\n"
                + "        \"id\":1,\n"
                + "        \"phoneNumber\":\"13800000000\",\n"
                + "        \"version\":1\n"
                + "    },\n"
                + "    \"orderDetails\":{\n"
                + "        \"skuIds\":[\n"
                + "            1,\n"
                + "            2\n"
                + "        ]\n"
                + "    },\n"
                + "    \"orderStatus\":\"WAIT_PAY\",\n"
                + "    \"shipped\":false,\n"
                + "    \"totalActualPay\":88,\n"
                + "    \"totalShouldPay\":88,\n"
                + "    \"version\":1\n"
                + "}";
        Order o = JsonUtils.INSTANCE.readerFor(Order.class).readValue(orderJson);
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
