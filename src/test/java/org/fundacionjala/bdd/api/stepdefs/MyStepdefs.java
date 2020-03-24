package org.fundacionjala.bdd.api.stepdefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class MyStepdefs {

    private Response response;
    private Helper helper;

    public MyStepdefs(final Helper helper) {
        this.helper = helper;
    }

    @When("I send GET request to {string}")
    public void iSendGETRequestTo(final String endpoint) {
        response = given()
                .header("X-TrackerToken", EnvReader.getInstance().getApiToken())
                .header("Content-Type", "application/json")
                .when()
                .get(endpoint);
    }

    @When("I send POST request to {string} with body")
    public void iSendPOSTRequestToWithBody(final String endpoint, final String body) {
        response = given()
                .header("X-TrackerToken", EnvReader.getInstance().getApiToken())
                .header("Content-Type", "application/json")
                .when()
                .body(body)
                .post(endpoint);
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        String id = response.jsonPath().getString("id");
        helper.addNewId(id);
        assertEquals(actualStatusCode, expectedStatusCode);
    }

    @Then("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String pathSchema) {
        File schemaFile = new File(pathSchema);
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
    }
}
