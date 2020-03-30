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

public class JackyStepdefs {

    private Helper helper;
    private Response response;

    public JackyStepdefs(final Helper helper) {

        this.helper = helper;
    }

    @Given("I store response as {string}")
    public void iStoreResponseAs(final String id) {
        helper.setResponses(id, response);
    }

    @Given("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(final String endpoint) {
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", "New Board IntellIJ")
                .queryParam("desc", "new board created from API testing")
                .when()
                .post(endpoint);
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

    @When("I send a POST request to {string} with body")
    public void iSendAPOSTRequestTo(final String endpoint, final String body) {
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .body(body)
                .post(endpoint);
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestToWithBody(final String endpoint) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .delete(processedEndpoint);
    }

    @When("I send a PUT request to {string} with body")
    public void iSendAPUTRequestTo(final String endpoint, final String body) {
        String processedEndpoint = DynamicIdHelper.buildEndpoint(helper.getResponses(), endpoint);
        response = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("desc", "Description for this board was updated")
                .when()
                .body(body)
                .put(processedEndpoint);
    }

    @Then("response status code should be {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode);
        String id = response.jsonPath().getString("id");
        helper.setIds(id);
    }

    @And("response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String pathSchema) {
        File getSchemaFile = new File(pathSchema);
        response.then().assertThat().body(matchesJsonSchema(getSchemaFile));
    }

    @And("response should contain the following data")
    public void responseShouldContainTheFollowingData(final Map<String, String> expectedBody) {
        for (String id : expectedBody.keySet()) {
            assertEquals(response.jsonPath().getString(id), expectedBody.get(id));
        }
    }

}
