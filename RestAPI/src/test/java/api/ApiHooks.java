package api;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class ApiHooks {
    static String token;

    @Before("@api")
    public void setupApi() {
        RestAssured.baseURI = "https://gorest.co.in";

        token = System.getenv("GOREST_TOKEN");
        if (token == null || token.trim().isEmpty()) {
            token = System.getProperty("gorest.token");
        }
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalStateException(
                    "GoRest token is required. Set env GOREST_TOKEN or pass -Dgorest.token=YOUR_TOKEN");
        }
    }
}
