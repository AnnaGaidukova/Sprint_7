import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.apache.commons.lang3.RandomStringUtils;
import static io.restassured.RestAssured.given;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.*;

public class CourierTest {
    String randomLogin = RandomStringUtils.random(10, true, false);
    String randomName = RandomStringUtils.random(10, true, false);
    @Before
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test

    public void checkCourierResponseBody() {
        //проверка успешного создания курьера
        Courier courier = new Courier(randomLogin, "Test123!", randomName);

        Response response1 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response1.then().assertThat().body("ok", Matchers.is(true))
                .and()
                .statusCode(201);
        System.out.println(response1.body().asString());

        CourierLogin courierLogin = new CourierLogin(randomLogin, "Test123!");
         Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");

        responseLogin.then().assertThat().body("id", isA(Integer.class))
                .and()
                .statusCode(200);

        String IdString = responseLogin.body().asString();
        Gson gson = new Gson();
        CourierDelete id = gson.fromJson(IdString, CourierDelete.class);


        Response responseDelete = given()
                .header("Content-type", "application/json")
                .when()
                .delete(String.format("/api/v1/courier/%s", id.getId()));

        responseDelete.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }
    @Test
    public void checkCourierDoubleResponseBodyTest() {
        //тест создания курьера с существующим логином
        Courier courier = new Courier(randomLogin, "Test123!", randomName);

        Response response1 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response1.then().assertThat().body("ok", Matchers.is(true))
                .and()
                .statusCode(201);


        Response response2 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response2.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(409);

        System.out.println(response2.body().asString());
    }
    @Test
    public void checkCourierResponseWithoutFirstName() {
        // тест создания курьера без логина
                Courier courier = new Courier(randomLogin, "Test123!", "");

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
     //   System.out.println(response.body().asString());
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

        System.out.println(response.body().asString());
    }

    @Test
    public void checkCourierResponseWithoutPassword() {
        //тест создания курьера без пароля
        Courier courier = new Courier(randomLogin, "", randomName);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

        System.out.println(response.body().asString());
    }
    @Test
    public void checkCourierResponseWithoutLogin() {
        //тест создания курьера без имени
        Courier courier = new Courier("", "Test123!", randomName);

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);

        System.out.println(response.body().asString());
    }
}