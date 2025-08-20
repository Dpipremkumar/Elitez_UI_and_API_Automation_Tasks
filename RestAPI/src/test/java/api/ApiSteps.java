package api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiSteps {

    private String createdUserId;
    private String createdEmail;
    private String createdName;

    @Given("I have a valid GoRest access token")
    public void i_have_a_token() {
        Assertions.assertNotNull(ApiHooks.token, "Token must be set");
    }

    @When("I create a new user")
    public void i_create_a_new_user() {
        createdName = "Hari QA " + UUID.randomUUID().toString().substring(0, 6);
        createdEmail = "hari_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("name", createdName);
        body.put("gender", "male");
        body.put("email", createdEmail); // must be unique
        body.put("status", "active");

        Response resp =
                given()
                        .header("Authorization", "Bearer " + ApiHooks.token)
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/public/v2/users")
                        .then()
                        .statusCode(201)
                        .body("name", equalTo(createdName))
                        .body("email", equalTo(createdEmail))
                        .body("status", equalTo("active"))
                        .extract().response();

        createdUserId = String.valueOf(resp.path("id"));
    }

    @Then("the user is created successfully")
    public void user_created_ok() {
        Assertions.assertNotNull(createdUserId, "User id should not be null");
        Assertions.assertTrue(createdUserId.trim().length() > 0, "User id should not be blank");
    }

    @When("I fetch that user by id")
    public void i_fetch_that_user_by_id() {
        // no-op; validated in next step
    }

    @Then("the user details are returned")
    public void user_details_returned() {
        given()
                .header("Authorization", "Bearer " + ApiHooks.token)
                .accept(ContentType.JSON)
                .when()
                .get("/public/v2/users/{id}", createdUserId)
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(createdUserId)))
                .body("email", equalTo(createdEmail))
                .body("name", equalTo(createdName))
                .body("status", anyOf(equalTo("active"), equalTo("inactive")));
    }

    @When("I update that user's details")
    public void i_update_that_user() {
        String newName = createdName + " Updated";
        Map<String, Object> patch = new HashMap<String, Object>();
        patch.put("name", newName);
        patch.put("status", "inactive");

        given()
                .header("Authorization", "Bearer " + ApiHooks.token)
                .contentType(ContentType.JSON)
                .body(patch)
                .when()
                .patch("/public/v2/users/{id}", createdUserId)
                .then()
                .statusCode(200)
                .body("id", equalTo(Integer.parseInt(createdUserId)))
                .body("name", equalTo(newName))
                .body("status", equalTo("inactive"));
    }
}
