import static org.hamcrest.CoreMatchers.notNullValue;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

public class OrdersTrackTest {
    OrderApi orderApi = new OrderApi();
    @Test
    @DisplayName("Positive test check orders")
    @Description("Checking response body")
    public void checkOrdersResponseBodyTest() {
        orderApi.getOrder()
                .then()
                .body("orders", notNullValue());
    }
}