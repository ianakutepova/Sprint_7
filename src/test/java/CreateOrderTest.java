import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {
    private String track;

    @Parameterized.Parameter
    public String[] color; // Изменили тип на String[]

    @Parameterized.Parameters
    public static Collection<Object[]> dataForColor() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}}, // для случая без цвета
                {new String[]{"BLACK", "GREY"}} // для случая с двумя цветами
        });
    }

    @Test
    public void testCreateOrderWithSingleColor() {
        Faker faker = new Faker();

        OrderModel order;
        if (color == null || color.length == 0) {
            order = new OrderModel(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.address().streetAddress(),
                    "Театральная",
                    faker.phoneNumber().phoneNumber(),
                    5,
                    "2026-06-20",
                    faker.lorem().sentence(),
                    null
            );
        } else {
            order = new OrderModel(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.address().streetAddress(),
                    "Театральная",
                    faker.phoneNumber().phoneNumber(),
                    5,
                    "2026-06-20",
                    faker.lorem().sentence(),
                    color
            );
        }

        Response response = createOrder(order)
                .then()
                .log().all()
                .statusCode(201)
                .body("track", notNullValue())
                .extract().response();

        String track = response.jsonPath().get("track");


        OrderCancellationTest.cancelOrder(track)
                .then()
                .log().all()
                .statusCode(200);
    }
    @After
    public void cleanUp() {
        if (track != null) {
            OrderCancellationTest.cancelOrder(track);
        }
    }
}
