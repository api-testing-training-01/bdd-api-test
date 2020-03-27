package org.fundacionjala.bdd.api.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.DynamicIdHelper;
import org.fundacionjala.bdd.api.utils.Helper;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class MyStepdefs {

    private Response response;
    private Helper helper;

    public MyStepdefs(final Helper helper) {
        this.helper = helper;
    }

    @Given("I store response as {string}")
    public void storeResponseAs(final String key) {
        helper.addResponse(key, response);
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

    @When("I send DELETE request to {string} with body")
    public void iSendDELETERequestToWithBody(final String endpoint) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .header("X-TrackerToken", EnvReader.getInstance().getApiToken())
                .header("Content-Type", "application/json")
                .when()
                .delete(processedEndpoint);
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode);
        if (!response.getBody().asString().isEmpty()) {
            String id = response.jsonPath().getString("id");
            helper.addNewId(id);
        }
    }

    @Then("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String pathSchema) {
        File schemaFile = new File(pathSchema);
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
    }

    @Then("Response should contain the following data")
    public void responseShouldContainTheFollowingData(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            assertEquals(response.jsonPath().getString(key), expectedData.get(key),
                    String.format("The '%s' field does not match with expected value.", key));
        }
    }
}
