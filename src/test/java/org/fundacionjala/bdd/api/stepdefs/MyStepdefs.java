package org.fundacionjala.bdd.api.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.DynamicIdHelper;
import org.fundacionjala.bdd.api.utils.Helper;
import org.testng.AssertJUnit;

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

    @When("I send POST request to {string} with name")
    public void iSendPOSTRequestTo(final String endpoint, final String name) {
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", name)
                .when()
                .post(endpoint);
        String boardId = response.jsonPath().getString("id");
        helper.addSingleId(boardId);
        helper.addResponse("PostResponse", response);
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int statusExpected) {
        assertEquals(statusExpected, response.getStatusCode());
    }

    @And("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String schemaPath) {
        response.then().assertThat().body(matchesJsonSchema(new File(schemaPath)));
    }

    @When("I send DELETE request to {string}")
    public void iSendDELETERequestTo(final String endpoint) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .delete(processedEndpoint);
    }

    @When("I send GET request to {string}")
    public void iSendGETRequestTo(final String endpoint) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .get(processedEndpoint);
    }

    @When("I send PUT request to {string} to desc")
    public void iSendPUTRequestTo(final String endpoint, final String text) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("desc", text)
                .when()
                .put(processedEndpoint);
    }

    @Then("Response should contain data")
    public void responseShouldContainData(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            AssertJUnit.assertEquals(response.jsonPath().getString(key), expectedData.get(key));
        }
    }
}
