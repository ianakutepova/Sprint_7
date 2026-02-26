import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {
    private String track;

    @Parameterized.Parameter
    public String[] color;

    @Parameterized.Parameters
    public static Collection<Object[]> dataForColor() {
        return Arrays.asList(new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}},
                {new String[]{"BLACK", "GREY"}}
        });
    }

    @Test
    @Step("Create order with single color")
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

        this.track = response.jsonPath().get("track").toString();
        System.out.println("Track number: " + this.track);

        OrderSteps.cancelOrder(this.track)
                .then()
                .log().all()
                .statusCode(200);
    }
    @After
    public void cleanUp() {
        if (track != null) {
            OrderSteps.cancelOrder(track);
        }
    }
}
