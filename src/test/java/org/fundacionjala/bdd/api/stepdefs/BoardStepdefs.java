package org.fundacionjala.bdd.api.stepdefs;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.utils.DynamicIdHelper;
import org.fundacionjala.bdd.api.utils.SharedData;
import org.testng.AssertJUnit;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class BoardStepdefs {

    private SharedData sharedData;
    private Response bodyResponse;

    public BoardStepdefs(final SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @Given("I send POST request to {string} to create a board with body")
    public void iSendPOSTRequestToToCreateABoardWithBodyy(final String urlRequest, final String body) {
        bodyResponse = given()
                .spec(sharedData.getHeaderResponse())
                .body(body)
                .post(urlRequest);
        sharedData.setStatusCode(bodyResponse.getStatusCode());
    }

    @Given("Setting the {string}")
    public void settingThe(final String boardID) {
        sharedData.setResponse(boardID, bodyResponse);
    }

    @When("I send POST request to {string} to create a board with {string} name")
    public void iSendPOSTRequestToToCreateABoardWithName(final String urlRequest, final String name) {
        bodyResponse = given()
                .spec(sharedData.getHeaderResponse())
                .body("{\"name\":\"" + name + "\",\"desc\":\"testing... boards\"}")
                .post(urlRequest);
        sharedData.setStatusCode(bodyResponse.getStatusCode());
    }

    @When("I send PUT request to {string} to update {string} name")
    public void iSendPUTRequestToToUpdateName(final String url, final String newName) {
        String endPoint = DynamicIdHelper.buildEndpoint(sharedData.getResponse(), url);
        bodyResponse = given()
                .spec(sharedData.getHeaderResponse())
                .queryParam("name", newName)
                .put(endPoint);
        sharedData.setStatusCode(bodyResponse.getStatusCode());
    }

    @When("I send GET request to {string} body board")
    public void iSendGETRequestToBodyBoard(final String url) {
        String endPoint = DynamicIdHelper.buildEndpoint(sharedData.getResponse(), url);
        bodyResponse = given()
                .spec(sharedData.getHeaderResponse())
                .get(endPoint);
        sharedData.setStatusCode(bodyResponse.getStatusCode());
    }

    @When("I send Delete request to {string} to delete a board already created")
    public void iSendDeleteRequestToToDeleteABoardAlreadyCreated(final String url) {
        String endPoint = DynamicIdHelper.buildEndpoint(sharedData.getResponse(), url);
        bodyResponse = given()
                .spec(sharedData.getHeaderResponse())
                .delete(endPoint);
        sharedData.setStatusCode(bodyResponse.getStatusCode());
    }

    @Then("Status code should be {int}")
    public void statusCodeShouldBe(final int expectedStatusCode) {
        AssertJUnit.assertEquals(sharedData.getStatusCode(), expectedStatusCode);
    }

    @Then("Response should match with {string} json schema")
    public void responseShouldMatchWithJsonSchema(final String pathSchema) {
        File boardSchemaFile = new File(pathSchema);
        bodyResponse.then().assertThat().body(matchesJsonSchema(boardSchemaFile));
    }

    @Then("Response should contain data")
    public void responseShouldContainData(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            AssertJUnit.assertEquals(bodyResponse.jsonPath().getString(key), expectedData.get(key));
        }
    }
}
