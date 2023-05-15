import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.Test;


public class OrdersTrackTest {
    OrderApi orderApi = new OrderApi();
    @Test
    public void checkOrdersResponseBodyTest() {
        orderApi.getOrder()
                .then()
                .body("orders", notNullValue());
    }
}