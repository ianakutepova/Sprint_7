package steps;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static final String ORDER_PATH = "/api/v1/orders";

    public static Response createOrder(OrderModel orderModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderModel)
                .when()
                .post(ORDER_PATH)
                .then()
                .extract().response();

    }
    public static final String CANCEL_ORDER_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/orders/cancel";

    public static Response cancelOrder(String track) {
        return RestAssured.given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .put(CANCEL_ORDER_PATH + "?track=" + track)
                .then()
                .extract().response();
    }
}