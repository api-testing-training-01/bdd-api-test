package org.fundacionjala.bdd.api.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class JackyStepdefs {

    private Helper helper;
    private Response res;

    public JackyStepdefs(final Helper helper) {
        this.helper = helper;
    }

    @When("I send GET request to {string}")
    public void iSendGETRequestTo(final String endpoint) {
        res = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .get(endpoint.concat(helper.getId()));

    }

    @When("I send a POST request to {string}")
    public void iSendAPOSTRequestTo(final String endpoint) {
        res = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .body("{\"name\":\"New Board Created\",\"desc\":\"new board created from intellij\"}")
                .post(endpoint);
            helper.setId(res.jsonPath().getString("id"));
    }

    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(final String endpoint) {
        res = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .delete(endpoint.concat(helper.getId()));
    }

    @When("I send a PUT request to {string}")
    public void iSendAPUTRequestTo(final String endpoint) {
        res = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("desc", "Description for this board was updated")
                .when()
                .put(endpoint.concat(helper.getId()));
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = res.getStatusCode();
        assertEquals(actualStatusCode, expectedStatusCode);
    }

    @And("response body should match with {string} json schema")
    public void responseBodyShouldMatchWithJsonSchema(final String pathSchema) {
        File getSchemaFile = new File(pathSchema);
        res.then().assertThat().body(matchesJsonSchema(getSchemaFile));
    }


}
