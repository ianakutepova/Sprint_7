import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OrderCancellationTest {
    public static final String CANCEL_ORDER_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/orders/cancel";

    public static Response cancelOrder(String track) {
        return RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\"track\": \"" + track + "\"}")
                .when()
                .put(CANCEL_ORDER_PATH)
                .then()
                .extract().response();
    }
}