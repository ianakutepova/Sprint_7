import io.qameta.allure.Step;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;

import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.createCourier;

public class CreateCourierTest extends BaseApiTest {
    private static String courierLogin;
    private static String courierPassword;
    private static String courierFirstName;

        @Step("CreateCourierSuccess")
        @Test
        public void testCreateCourierSuccess() {

            CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);

            System.out.println(courier);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
        }

    @Step("CreateDuplicateCourier")
    @Test
    public void testCreateDuplicateCourierError() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        createCourier(courier);


        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT) // Пример кода ошибки конфликта (можно изменить на актуальный)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой.")); // Пример сообщения об ошибке
    }

    @Step("CreateCourierWithMissingLogin")
    @Test
    public void testCreateCourierWithMissingLoginError() {

        CourierModel courierWithoutLogin = new CourierModel(null, PASSWORD, FIRSTNAME);

        createCourier(courierWithoutLogin)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST) // Пример кода ошибки неверного запроса (можно изменить на актуальный)
                .body("message", equalTo("Недостаточно данных для создания учетной записи")); // Пример сообщения об ошибке
    }

    @Step("CreateCourierWithMissingPassword")
    @Test
    public void testCreateCourierWithMissingPasswordError() {

        CourierModel courierWithoutPassword = new CourierModel(LOGIN, null, FIRSTNAME);

        createCourier(courierWithoutPassword)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST) // Пример кода ошибки неверного запроса (можно изменить на актуальный)
                .body("message", equalTo("Недостаточно данных для создания учетной записи")); // Пример сообщения об ошибке
    }

    @Step("CreateCourierWithMissingFirstname")
    @Test
    public void testCreateCourierWithMissingFirstnameError() {

        CourierModel courierWithoutFirstname = new CourierModel(LOGIN, PASSWORD, null);

        createCourier(courierWithoutFirstname)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED) // Пример кода ошибки неверного запроса (можно изменить на актуальный)
                .body("ok", equalTo(true)); // Пример сообщения об ошибке
    }

    @After
    public void cleanUp() {
    }


    }

