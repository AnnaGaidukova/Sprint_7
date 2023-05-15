import org.junit.Test;
import org.junit.After;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
    String randomLogin = RandomStringUtils.random(10, true, false);
    String randomName = RandomStringUtils.random(10, true, false);
    Courier courier = new Courier(randomLogin, "Test123!", randomName);
    CourierApi courierApi = new CourierApi();
    private int courierId;
    private int courierIdSecond;

    @After
    public void deleteCourier() {
        courierApi.delete(courierId);
        courierApi.delete(courierIdSecond);
    }

    @Test

    public void checkCourierResponseBody() {
        //проверка успешного создания курьера
        Courier courier = new Courier(randomLogin, "Test123!", randomName);
        courierApi.createCourier(courier)
                .then().assertThat().body("ok", Matchers.is(true))
                .and()
                .statusCode(201);
    }
    @Test
    public void checkCourierDoubleResponseBodyTest() {
        //тест создания курьера с существующим логином
        Courier courier = new Courier(randomLogin, "Test123!", randomName);
        courierApi.createCourier(courier);
        courierApi.createCourier(courier)
                .then().assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);
    }
    @Test
    public void checkCourierResponseWithoutFirstName() {
        // тест создания курьера без имени
        courier = new Courier(randomLogin, "Test123!", "");
        courierApi.createCourier(courier)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    public void checkCourierResponseWithoutPassword() {
        //тест создания курьера без пароля
        courier = new Courier(randomLogin, "", randomName);
        courierApi.createCourier(courier)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
    @Test
    public void checkCourierResponseWithoutLogin() {
        //тест создания курьера без логина
        courier = new Courier("", "Test123!", randomName);
        courierApi.createCourier(courier)
                .then().assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }
}