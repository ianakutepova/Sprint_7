package steps;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import model.LoginCourierModel;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    public static final String CREATE_PATH = "/api/v1/courier";

    @Step
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

    @Step
    public static Response loginCourier(LoginCourierModel loginModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(loginModel)
                .when()
                .post(CREATE_PATH + "/login")
                .then()
                .extract().response();

    }

    @Step
    public static void deleteCourier(String id) {
        String deleteCourierPath = (CREATE_PATH + "/" + id);
        Response response = RestAssured.given()
                .log().all()
                .when()
                .delete(deleteCourierPath)
                .then()
                .extract().response();

    }
}