package org.fundacionjala.bdd.api.stepdefs.jorge;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.jorge.EndPointBuilder;
import org.fundacionjala.bdd.api.utils.jorge.Helper;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class TrelloStepDefinition {

    private Response response;
    private Helper helper;

    public TrelloStepDefinition(final Helper helper) {
        this.helper = helper;
    }


    @Given("I send POST request to {string} with following data")
    public void iSendPOSTRequestToWithFollowingData(final String endPoint, final Map<String, String> param) {
        response = requestBuilderWithParam(param).post(endPoint);
    }

    @When("I send GET request to {string}")
    public void getRequest(final String endPoint) {
        response = requestBuilderWithParam(null).get(EndPointBuilder.build(helper.getResponseMap(), endPoint));
    }

    @When("I send PUT request to {string}")
    public void iSendPUTRequestTo(final String endPoint) {
        response = requestBuilderWithParam(null)
                    .put(EndPointBuilder.build(helper.getResponseMap(),
                                               helper.getParamValue(),
                                               endPoint));
    }

    @When("I send DELETE request to {string}")
    public void iSendDeleteRequest(final String endPoint) {
        response = requestBuilderWithParam(null)
                    .delete(EndPointBuilder.build(helper.getResponseMap(),
                                                  helper.getParamValue(),
                                                  endPoint));
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @Then("I store response as {string}")
    public void iStoreResponseAs(final String responseKey) {
        helper.addResponse(responseKey, response);
        helper.setKey(responseKey);
    }

    @Then("I store response as {string} updating the following fields")
    public void iStoreResponseAsWithTheTheFollowingDataUpdated(final String responseKey,
                                                               final Map<String, String> newData) {
        helper.addResponse(responseKey, response);
        helper.setParamValue(newData);
    }

    @And("Response should contain the following data")
    public void responseShouldContainTheFollowingData(final Map<String, String> paramExpected) {
        for (Map.Entry<String, String> entry : paramExpected.entrySet()) {
            String actualResult = response.getBody().jsonPath().getString(entry.getKey());
            assertEquals(actualResult, entry.getValue());
        }
    }

    @And("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String pathSchema) {
        File schemaFile = new File(pathSchema);
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
    }

    @And("Updating the following fields")
    public void updatingTheFollowingFields(final Map<String, String> newData) {
        Response res = helper.getResponseMap().get(helper.getKey());
        for (Map.Entry<String, String> entry : newData.entrySet()) {
            String actualName = res.getBody().jsonPath().getString(entry.getKey());
            res.getBody().jsonPath().getString(entry.getKey()).replaceAll(actualName, entry.getValue());
        }
    }

    public static RequestSpecification requestBuilderWithParam(final Map<String, String> param) {
        RequestSpecification requestResult = given()
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken());

        if (param == null) {
            return requestResult.when();
        }

        for (Map.Entry<String, String> entry : param.entrySet()) {
            requestResult.queryParam(entry.getKey(), entry.getValue());
        }
        return requestResult.when();
    }
}
