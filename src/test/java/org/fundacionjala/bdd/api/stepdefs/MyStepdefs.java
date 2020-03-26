package org.fundacionjala.bdd.api.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;
import org.testng.AssertJUnit;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class MyStepdefs {

    private Helper helper;

    public MyStepdefs(final Helper helper) {
        this.helper = helper;
    }

    @Given("Valid token for {string}")
    public void validTokenFor(final String urlTarget) {
        RequestSpecification headerResponse = given()
                .baseUri(urlTarget)
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey());
        helper.setHeaderResponse(headerResponse);
    }

    @When("I send POST request to {string} to create a board with {string} name")
    public void iSendPOSTRequestToToCreateABoardWithName(final String urlRequest, String name) {
        Response bodyResponse= given()
                .spec(helper.getHeaderResponse())
                .body("{\"name\":\"" + name + "\",\"desc\":\"testing... boards\"}")
                .post(urlRequest);
        JsonPath bodyResponseBoards = new JsonPath(bodyResponse.getBody().asString());
        String boardId = bodyResponseBoards.getString("id");
        helper.setBoardId(boardId);
        helper.setStatusCode(bodyResponse.getStatusCode());
        helper.setBodyResponse(bodyResponse);
    }

    @Then("Status code should be {int}")
    public void statusCodeShouldBe(final int expectedStatusCode) {
        AssertJUnit.assertEquals(helper.getStatusCode(), expectedStatusCode);
    }

    @And("Response should match with {string} json schema")
    public void responseShouldMatchWithJsonSchema(final String pathSchema) {
        File boardSchemaFile = new File(pathSchema);
        helper.getBodyResponse()
                .then()
                .assertThat()
                .body(matchesJsonSchema(boardSchemaFile));
    }

    @When("I send PUT request to {string} to update {string} name")
    public void iSendPUTRequestToToUpdateName(String url, String newName) {
        Response bodyResponse = given()
                .spec(helper.getHeaderResponse())
                .queryParam("name", newName)
                .put(url + helper.getId());
        helper.setStatusCode(bodyResponse.getStatusCode());
        helper.setBodyResponse(bodyResponse);
    }

    @When("I send GET request to {string} body board")
    public void iSendGETRequestToBodyBoard(String url) {
        Response bodyResponse = given()
                .spec(helper.getHeaderResponse())
                .get(url + helper.getId());
        helper.setStatusCode(bodyResponse.getStatusCode());
        helper.setBodyResponse(bodyResponse);
    }

    @When("I send Delete request to {string} to delete a board already created")
    public void iSendDeleteRequestToToDeleteABoardAlreadyCreated(String url) {
        Response bodyResponse = given()
                .baseUri("https://api.trello.com")
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .delete(url + helper.getId());
        helper.setStatusCode(bodyResponse.getStatusCode());
        helper.setBodyResponse(bodyResponse);
    }
}
