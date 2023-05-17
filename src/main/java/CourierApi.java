import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierApi {

    public CourierApi() {
        RestAssured.baseURI = Config.Urls.BASE_URI;
    }
    private static final String COURIER_URI = "api/v1/courier/";

    @Description("for testing creating courier")
    public Response createCourier(Courier courier) {
        return given().header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_URI);
    }

    @Description("for testing authorizing courier")
    public Response loginCourier(CourierLogin courierLogin) {
        return given().header("Content-type", "application/json")
                .and()
                .body(courierLogin)
                .when()
                .post(COURIER_URI + "login/");
    }

    @Description("for testing deleting courier")
    public ValidatableResponse delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_URI + id)
                .then();
    }
    public ValidatableResponse delete() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_URI)
                .then();
    }
}

