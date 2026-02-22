import data.TestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static org.hamcrest.CoreMatchers.equalTo;
import static data.TestData.*;

public class LoginCourierTest extends BaseApiTest {

    public static final String COURIER_LOGIN = "/api/v1/courier/login";

    @Step("LoginCourier")
    @Test
    public void testLoginCourierSuccess() {
        System.out.println("Trying to log in with login: " + LOGIN + ", password: " + PASSWORD);
        Response response = loginCourier(TestData.KNOWN_USER, TestData.KNOWN_PASSWORD)
                .then()
                .statusCode(200)
                .body("id", equalTo("some_id"))
                .extract().response();
    }

    private Response loginCourier(String login, String password) {
        return given()
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password))
                .when()
                .post(COURIER_LOGIN);
    }

    @Step("LoginCourierWithMissingFields")
    @Test
    public void testLoginCourierWithMissingFieldsError() {

        given()
                .body("{\"password\": \"" + PASSWORD + "\"}")
                .when()
                .post(COURIER_LOGIN)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));


        given()
                .body("{\"login\": \"" + LOGIN + "\"}")
                .when()
                .post(COURIER_LOGIN)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("LoginCourierWithIncorrectCredentials")
    @Test
    public void testLoginCourierWithIncorrectCredentialsError() {

        given()
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", INCORRECT_LOGIN, INCORRECT_PASSWORD))
                .when()
                .post(COURIER_LOGIN)
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
                .body(String.format("{\"login\": \"%s\", \"password\": \"%s\"}", randomLogin, randomPassword))
                .when()
                .post(COURIER_LOGIN)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
