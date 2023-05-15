import static org.hamcrest.Matchers.isA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)

public class OrdersTest {
    OrderApi orderApi = new OrderApi();
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public OrdersTest(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters(name = "ТестЦвета: {8}")
    public static Object[][] params() {
        return new Object[][] {
                {"ТестТест", "Тестов", "Космонавтов 12", 6, "89111111111", 1, "2023-05-10", "комментарий для курьера", List.of("BLACK")},      //тест с черным цветом
                {"ТестТест", "Тестов", "Космонавтов 12", 6, "89111111111", 1, "2023-05-10", "комментарий для курьера", List.of("GRAY")},       //тест с серым цветом
                {"ТестТест", "Тестов", "Космонавтов 12", 6, "89111111111", 1, "2023-05-10", "комментарий для курьера", List.of()},                //тест без цвета
                {"ТестТест", "Тестов", "Космонавтов 12", 6, "89111111111", 1, "2023-05-10", "комментарий для курьера", List.of("BLACK", "GRAY")}  //тест с двумя цветами
        };
    }

    @Test
    public void checkOrdersResponseBody() {
        Orders orders = new Orders(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        orderApi.createOrder(orders)
                .then().assertThat().body("track", isA(Integer.class))
                .and()
                .statusCode(201);
    }
}