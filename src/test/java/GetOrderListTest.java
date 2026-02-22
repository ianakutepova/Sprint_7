import io.qameta.allure.Step;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static steps.OrderSteps.ORDER_PATH;

public class GetOrderListTest extends BaseApiTest {

    @Step("GetOrderList")
    @Test
    public void testGetOrderList() {
        given()
                .when()
                .get(ORDER_PATH)
                .then()
                .statusCode(200)
                .body("orders", hasKey("id"))
                .body("pageInfo", hasKey("page"))
                .body("availableStations", hasKey("name"));
    }
}
