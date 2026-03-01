import data.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.CourierModel;
import model.LoginCourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static data.TestData.*;
import static steps.CourierSteps.*;

public class LoginCourierTest extends BaseApiTest {
    private static String login;
    private static String password;
    private static String firstName;

    private static String courierLoginId;

    @Before
    @Step("Setup a new courier")
    public void setUp() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);
        LoginCourierModel loginModel = new LoginCourierModel (LOGIN, PASSWORD);
        courierLoginId = loginCourier(loginModel).jsonPath().getString("id");
    }

    @After
    @Step("Delete the courier after test")
    public void tearDown() {
        deleteCourier(courierLoginId);
    }

    @Test
    @DisplayName("Test successful courier login")
    @Description("Checks the success of courier login with correct credentials")
    public void testLoginCourierSuccess() {
        System.out.println("Trying to log in with login: " + LOGIN + ", password: " + PASSWORD);
        LoginCourierModel loginModel = new LoginCourierModel(LOGIN, PASSWORD);
        Response response = CourierSteps.loginCourier(loginModel);

        response.then()
                .statusCode(HTTP_OK);
    }


    @Test
    @DisplayName("Test login with missing fields")
    @Description("Checks error when logging in with missing fields")
    public void testLoginCourierWithMissingFieldsError() {
        LoginCourierModel missingLoginModel = new LoginCourierModel(null, PASSWORD);
        Response response = CourierSteps.loginCourier(missingLoginModel);

        response.then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        LoginCourierModel missingPasswordModel = new LoginCourierModel(LOGIN, null);
        Response response2 = CourierSteps.loginCourier(missingPasswordModel);

        response2.then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Test login with incorrect credentials")
    @Description("Checks error when logging in with incorrect credentials")
    public void testLoginCourierWithIncorrectCredentialsError() {
        LoginCourierModel incorrectCredentialsModel = new LoginCourierModel(INCORRECT_LOGIN, INCORRECT_PASSWORD);
        Response response = CourierSteps.loginCourier(incorrectCredentialsModel);

        response.then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Test login for non-existing user")
    @Description("Checks error for non-existing user login")
    public void testLoginNonExistingUserError() {
        String randomLogin = TestData.NON_EXISTING_LOGIN;
        String randomPassword = TestData.NON_EXISTING_PASSWORD;
        LoginCourierModel nonExistingUserModel = new LoginCourierModel(randomLogin, randomPassword);
        Response response = CourierSteps.loginCourier(nonExistingUserModel);

        response.then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
