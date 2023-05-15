import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    public OrderApi() {
        RestAssured.baseURI = Config.Urls.BASE_URI;;
    }
    private static final String ORDER_URI = "/api/v1/orders";

    public Response createOrder(Orders orders) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orders)
                .and()
                .when()
                .post("/api/v1/orders");
    }
    public Response getOrder() {
        return given().get("ORDER_URI");
    }
}
