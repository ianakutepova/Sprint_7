import io.restassured.RestAssured;
import org.junit.After;
import org.junit.BeforeClass;

import static data.TestData.BASE_URI;

public class BaseApiTest {


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        System.out.println("Base URI set to: " + BASE_URI);

    }

    @After
    public void cleanUp() {
    }

}
