import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.*;

public class CourierLoginTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        Courier courier = new Courier("TestTest123@test.com", "Test123!", "Testov");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .and()
                .when()
                .post("/api/v1/courier");
        response.then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

   @After
    public void tearDown(){
        CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "Test123!");
        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .and()
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
    public void checkCourierLoginResponceBody() {
        //тест успешной авторизации
        CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "Test123!");
        Response responseLogin = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post("/api/v1/courier/login");
        System.out.println(responseLogin.body().asString());

        responseLogin.then().assertThat().body("id", isA(Integer.class))
                .and()
                .statusCode(200);

        System.out.println(responseLogin.body().asString());
    }

    @Test
    public void checkCourierNonExistentLogin() {
        //тест неверного значения в поле логин
        CourierLogin courierLogin = new CourierLogin("testcouriertesttestcourier123@test", "Test123!");
        Response responseLogin1 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .and()
                .when()
                .post("/api/v1/courier/login");
        responseLogin1.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println(responseLogin1.body().asString());
    }

    @Test
    public void checkCourierNonExistentPassword() {
        //тест неверного значения в поле пароль
        CourierLogin courierLogin = new CourierLogin("testcouriertesttestcourier123@test", "Test123");
        Response responseLogin12 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .and()
                .when()
                .post("/api/v1/courier/login");
        responseLogin12.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
        System.out.println(responseLogin12.body().asString());
    }
    @Test
    public void checkCourierWithoutLogin() {
        //тест авторизации без логина
        CourierLogin courierLogin = new CourierLogin("", "Test123!");
        Response responseLogin2 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .and()
                .when()
                .post("/api/v1/courier/login");
        responseLogin2.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(responseLogin2.body().asString());
    }

    @Test
    public void checkCourierWithoutPassword() {
        //тест авторизации без пароля
        CourierLogin courierLogin = new CourierLogin("TestTest123@test.com", "");
        Response responseLogin3 = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .and()
                .when()
                .post("/api/v1/courier/login");
        responseLogin3.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
        System.out.println(responseLogin3.body().asString());
    }


    }