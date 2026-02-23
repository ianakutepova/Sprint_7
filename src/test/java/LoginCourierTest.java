import data.TestData;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import model.LoginCourierModel;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static data.TestData.*;
import static steps.CourierSteps.CREATE_PATH;

public class LoginCourierTest extends BaseApiTest {


    @Step("LoginCourier")
    @Test
    public void testLoginCourierSuccess() {
        System.out.println("Trying to log in with login: " + LOGIN + ", password: " + PASSWORD);
        Response createResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(new CourierModel(LOGIN, PASSWORD, FIRSTNAME))
                .when()
                .post(CREATE_PATH)
                .then()
                .extract().response();

        if (createResponse.statusCode() == 200) {
            String courierId = createResponse.jsonPath().getString("id");
            System.out.println("Курьер успешно создан с ID: " + courierId);
        } else {
            System.out.println("Ошибка при создании курьера: " + createResponse.statusCode());
            return;
        }
        Response response = given()
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", LOGIN, PASSWORD))
                .when()
                .post(CREATE_PATH + "login")
                .then()
                .statusCode(HTTP_CREATED)
                .body("id", equalTo("some_id"))
                .extract().response();
    }


    @Step("LoginCourierWithMissingFields")
    @Test
    public void testLoginCourierWithMissingFieldsError() {

        given()
                .log().all()
                .body("{\"password\": \"" + PASSWORD + "\"}")
                .when()
                .post(CREATE_PATH + "login")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));


        given()
                .log().all()
                .body("{\"login\": \"" + LOGIN + "\"}")
                .when()
                .post(CREATE_PATH + "login")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("LoginCourierWithIncorrectCredentials")
    @Test
    public void testLoginCourierWithIncorrectCredentialsError() {

        given()
                .log().all()
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", INCORRECT_LOGIN, INCORRECT_PASSWORD))
                .when()
                .post(CREATE_PATH + "login")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("LoginNonExistingUser")
    @Test
    public void testLoginNonExistingUserError() {
        String randomLogin = TestData.NON_EXISTING_LOGIN;
        String randomPassword = TestData.NON_EXISTING_PASSWORD;

        given()
                .log().all()
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", randomLogin, randomPassword))
                .when()
                .post(CREATE_PATH + "login")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
