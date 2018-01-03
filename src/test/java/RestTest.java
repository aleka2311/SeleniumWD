import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RestTest {
    @BeforeTest
    public void initTest() {
        RestAssured.baseURI = "http://jsonplaceholder.typicode.com";
    }

    @Test
    public void checkStatusCode() {
        Response response = given().get("/users").andReturn();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void checkHeader() {
        Response response = given().get("/users").andReturn();
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
    }

    @Test
    public void checkArray() {
        Response response = given().get("/users").andReturn();
        List<Integer> a = new ArrayList<Integer>();
        a.addAll((Collection<? extends Integer>) response.getBody().jsonPath().getJsonObject("id"));
        Assert.assertEquals(a.size(), 10);
    }
}
