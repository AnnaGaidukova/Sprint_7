import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import java.net.http.HttpResponse;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;


public class CourierLoginTest {
    Courier courier = new Courier("TestTest123@test.com", "Test123!", "Testov");
    CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "Test123!");
    CourierApi courierApi = new CourierApi();
    private int courierId;

   @After
   /*public void tearDown() {
       courierApi.loginCourier(courierLogin);
   } */
   public void deleteCourier() {
       courierApi.delete(courierId);
   }

//тест успешной авторизации
    @Test
    @DisplayName("Positive test for authorized courier")
    @Description("Checking response body and status code 200")
    public void checkCourierLoginResponseBody() {
        //тест успешной авторизации
        CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "Test123!");
        courierApi.loginCourier(courierLogin)
                .then().assertThat().body("id", isA(Integer.class))
                .and()
                .statusCode(200);
    }
    //("id", isA(Integer.class))

    @Test
    @DisplayName("Negative test for authorized courier with incorrect login")
    @Description("Checking response body and status code 404")
    public void checkCourierNonExistentLogin() {
        //тест неверного значения в поле логин
        CourierLogin courierLogin = new CourierLogin("testcouriertesttestcourier123@test", "Test123!");
        courierApi.loginCourier(courierLogin)
                .then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);

    }

    @Test
    @DisplayName("Negative test for authorized courier with incorrect password")
    @Description("Checking response body and status code 404")
    public void checkCourierNonExistentPassword() {
        //тест неверного значения в поле пароль
        CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "Test123");
        courierApi.loginCourier(courierLogin)
                .then().assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }
    @Test
    @DisplayName("Negative test for authorized courier without login")
    @Description("Checking response body and status code 400")
    public void checkCourierWithoutLogin() {
        //тест авторизации без логина
        CourierLogin courierLogin = new CourierLogin("", "Test123!");
        courierApi.loginCourier(courierLogin)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Negative test for authorized courier without password")
    @Description("Checking response body and status code 400")
    public void checkCourierWithoutPassword() {
        //тест авторизации без пароля
        CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "");
        courierApi.loginCourier(courierLogin)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

}