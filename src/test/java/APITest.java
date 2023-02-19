import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class APITest {

    @Test
    @DisplayName("Should create entity using POST method")
    void createEntity() {
        RestAssured.baseURI ="https://variousitems.org/api";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "item1");
        requestBody.put("locale", "[“en”, “de”]");
        requestBody.put("is_verified", "true");
        requestBody.put("department_id", "3");

        given().
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                post("/items").
                then().
                statusCode(HttpStatus.SC_CREATED).
                body(
                        "_id", is("123"),
                        "name", is("item1"),
                        "locale", is("[“en”, “de”]"),
                        "is_verified", is("true"),
                        "department_id", is("3")
                );
    }

    @Test
    @DisplayName("Should update entity using PUT method")
    void updateEntity() {
        RestAssured.baseURI ="https://variousitems.org/api";
        String requestBody = "{\"name\":\"updatedName\"}";

        given().
                contentType(ContentType.JSON).
                body(requestBody).
                when().
                put("/items/{entity_id}", 123).
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "_id", is("123"),
                        "name", is("updatedName"),
                        "locale", is("[“en”, “de”]"),
                        "is_verified", is("true"),
                        "department_id", is("3")
                );
    }

    @Test
    @DisplayName("Should retrieve entity using GET method")
    void retrieveEntity() {
        RestAssured.baseURI ="https://variousitems.org/api";

        given().
                contentType(ContentType.JSON).
                when().
                get("/items/{entity_id}", 123).
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "_id", is("123"),
                        "name", is("updatedName"),
                        "locale", is("[“en”, “de”]"),
                        "is_verified", is("true"),
                        "department_id", is("3")
                );
    }
}
