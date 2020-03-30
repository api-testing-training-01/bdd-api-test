package org.fundacionjala.bdd.api.stepdefs;

import io.cucumber.java.en.And;
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

    @When("I send POST request to {string} with body")
    public void iSendPOSTRequestTo(final String endpoint, final String body) {
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .body(body)
                .when()
                .post(endpoint);
        String boardId = response.jsonPath().getString("id");
        helper.addNewId(boardId);
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int statusExpected) {
        assertEquals(statusExpected, response.getStatusCode());
    }

    @Then("Response should contain the following data")
    public void responseShouldContainTheFollowingData(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            assertEquals(response.jsonPath().getString(key), expectedData.get(key),
                    String.format("The '%s' field does not match with expected value.", key));
        }
    }

    @And("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String schemaJson) {
        response.then().assertThat().body(matchesJsonSchema(new File(schemaJson)));
    }

    @When("I send DELETE request to {string}")
    public void iSendDELETERequestTo(final String endpoint) {
        String endpointwithId = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .delete(endpointwithId);
    }

    @When("I send GET request to {string}")
    public void iSendGETRequestTo(final String endpoint) {
        String endpointwithId = endpoint.concat(helper.getIds().get(helper.getIds().size() - 1));
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .get(endpointwithId);
    }

    @When("I send PUT request to {string}")
    public void iSendPUTRequestTo(final String endpoint) {
        String endpointwithId = endpoint.concat(helper.getIds().get(helper.getIds().size() - 1));
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", "Board updated!")
                .when()
                .put(endpointwithId);
    }
}
