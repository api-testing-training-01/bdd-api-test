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

    @When("I send POST request to {string} with body")
    public void iSendPOSTRequestTo(final String endpoint, final String body) {
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", body)
                .when()
                .body(body)
                .post(endpoint);
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int statusExpected) {
        assertEquals(statusExpected, response.getStatusCode());
    }


    @When("I send a PUT request to {string} with body")
    public void iSendAPUTRequestTo(final String endpoint, final String body) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", body)
                .when()
                .body(body)
                .put(processedEndpoint);
    }

    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(final String endpoint) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .get(processedEndpoint);
    }


    @When("I send DELETE request to {string}")
    public void iSendDELETERequestTo(final String endpoint) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .header("Content-Type", "application/json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .delete(processedEndpoint);
    }


    @Then("Response should contain data")
    public void responseShouldContainData(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            AssertJUnit.assertEquals(response.jsonPath().getString(key), expectedData.get(key));
        }
    }

    @And("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String schemaPath) {
        response.then().assertThat().body(matchesJsonSchema(new File(schemaPath)));
    }


    @When("I store response as {string}")
    public void iStoreResponseAs(final String key) {
        helper.addResponse(key, response);
    }
}

