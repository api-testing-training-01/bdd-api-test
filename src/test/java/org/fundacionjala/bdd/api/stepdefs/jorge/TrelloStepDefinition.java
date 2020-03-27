package org.fundacionjala.bdd.api.stepdefs.jorge;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.jorge.Helper;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class TrelloStepDefinition {

    private Response response;
    private Helper helper;

    public TrelloStepDefinition(final Helper helper) {
        this.helper = helper;
    }

    // GET
    @When("I send GET request to {string}")
    public void getRequest(final String endPoint) {
        response = giveTrelloHeader().get(endPoint.concat(helper.getBoardID()));
    }

    //POST
    @When("I send POST request to {string} with body {string}")
    public void postRequest(final String endPoint, final String name) {
        response = given()
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("name", name)
                .post(endPoint);
        helper.setBoardID(getId(response.getBody().jsonPath().getString("shortUrl")));
    }

    //PUT
    @When("I send PUT request to {string} name {string}")
    public void putRequest(final String endPoint, final String newBoardName) {
        response = given()
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("name", newBoardName)
                .when()
                .put(endPoint.concat(helper.getBoardID()));
    }

    //DELETE
    @When("I send DELETE request to {string}")
    public void deleteRequest(final String endPoint) {
        response = given()
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .when()
                .delete(endPoint.concat(helper.getBoardID()));
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(final int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode);
    }

    @And("Response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String pathSchema) {
        File schemaFile = new File(pathSchema);
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
    }



    private static RequestSpecification giveTrelloHeader() {
        return given()
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .when();
    }

    private static String getId(final String url) {
        String[] urlArray = url.split("/");
        return urlArray[urlArray.length - 1];
    }
}
