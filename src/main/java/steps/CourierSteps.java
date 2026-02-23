package steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    public static final String CREATE_PATH = "/api/v1/courier";

    public static Response createCourier(CourierModel courierModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(CREATE_PATH)
                .then()
                .extract().response();

    }


    public static Response loginCourier(String login, String password) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password))
                .when()
                .post(CREATE_PATH + "login")
                .then()
                .extract().response();

    }
}
