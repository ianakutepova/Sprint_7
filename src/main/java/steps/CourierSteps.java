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

    public static final String COURIER_LOGIN = "/api/v1/courier/login";

    public static Response loginCourier(CourierModel courierModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(COURIER_LOGIN)
                .then()
                .extract().response();

    }
}
