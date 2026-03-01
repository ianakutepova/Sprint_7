import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import steps.OrderSteps;

import static org.hamcrest.Matchers.hasKey;

public class GetOrderListTest extends BaseApiTest {

    @Test
    @DisplayName("Get order list")
    @Description("Tests the retrieval of the order list")
    public void testGetOrderList() {
        Response response = OrderSteps.getOrderList();
        response.then()
                .log().all()
                .statusCode(200)
                .body("orders", hasKey("id"))
                .body("pageInfo", hasKey("page"))
                .body("availableStations", hasKey("name"));
    }
}
