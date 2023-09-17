import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.order.aggregate.Order;

/**
 * <br/>
 * Created on : 2023-09-17 21:54
 * @author mac
 */
public class MyTest {

    public static void main(String[] args) {
        String json = "{\"cancel\":false,\"currency\":\"CNY\",\"events\":null,\"excha1,\"id\":1,\"logs\":null,\"orderAddress\":{\"country\":\"CHINA\",\"email\":\"123456@qq.com\",\"fullAddressLine\":{\"addressLine1\":\"line1\",\"addressLine2\":\"line2\"},\"fullName\":{\"firstName\":\"first\",\"lastName\":\"last\"},\"id\":1,\"phoneNumber\":\"13800000000\",\"version\":1},\"orderDetails\":{\"skuIds\":[1,2]},\"orderStatus\":\"WAIT_PAY\",\"shipped\":false,\"totalActualPay\":88,\"totalShouldPay\":88,\"version\":1}";
        Order order = JsonUtils.json2JavaBean(json, Order.class);
        System.out.println(JsonUtils.toJSONString(order));
    }

}
