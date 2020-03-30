package org.fundacionjala.bdd.api.stepdefs.alexGarcia;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import org.fundacionjala.bdd.api.utils.alexGarcia.Helper;
import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ParamConfig.UpdateStrategy.REPLACE;
import static io.restassured.config.ParamConfig.paramConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class CRUDTrelloProject {
    private static Response response;

    private Helper helper;
    public CRUDTrelloProject(final Helper helper) {
        this.helper = helper;
    }

    @Given("I want a board with a '{}' as a '{}'")
    public void iWantABoardWithABDDTestAsAName(final String value, final String key) {
        helper.getInitialValues().config(config().paramConfig(paramConfig().queryParamsUpdateStrategy(REPLACE)))
                .queryParam(key, value);
    }

    @When("I send '{}' to Trello API with this information")
    public void iSendPOSTToTrelloAPIWithThisInformation(final String httpVerbs) {
        String  boardId;
        switch (httpVerbs) {
            case "POST" :
                response = helper.getInitialValues().post("/boards");
                boardId = response.jsonPath().getJsonObject("id");
                helper.addId(boardId);
                break;
            case "GET" :
                boardId = helper.getIds().get(helper.getIds().size() - 1);
                response = helper.getInitialValues().get("/boards/" + boardId);
                break;
            case "PUT" :
                boardId = helper.getIds().get(helper.getIds().size() - 1);
                response = helper.getInitialValues().put("/boards/" + boardId);
                break;
            case "DELETE" :
                boardId = helper.getIds().get(helper.getIds().size() - 1);
                response = helper.getInitialValues().delete("/boards/" + boardId);
                helper.removeId(boardId);
                break;
            default: System.out.println("Did not send a httpVerbs valid"); break;
        }
    }

    @Then("Response status code should be {int}")
    public void responseStatusCodeShouldBe(int statusCode) {
        int actualValue = response.getStatusCode();
        assertEquals(actualValue, statusCode);
    }

    @And("Response '{}' body should match with '{}' schema")
    public void responsePOSTBodyShouldMatchWithPOSTSchema(final String body, final String schema) {
        File schemaFile = null;
        switch (body) {
            case "POST" : schemaFile = new File("src/test/resources/schemas/alexGarcia/postSchema.json"); break;
            case "GET" : schemaFile = new File("src/test/resources/schemas/alexGarcia/getSchema.json"); break;
            case "PUT" : schemaFile = new File("src/test/resources/schemas/alexGarcia/putSchema.json"); break;
            case "DELETE" : schemaFile = new File("src/test/resources/schemas/alexGarcia/deleteSchema.json"); break;
            default: System.out.println("Did not send a httpVerbs valid"); break;
        }
        response.then().assertThat().body(matchesJsonSchema(schemaFile));
    }

    @Given("I have a board id")
    public void iHaveABoardId() {

    }

    @And("Response should contain the following data")
    public void responseShouldContainTheFollowingData(final Map<String, String> expectedData) {
        for (String key : expectedData.keySet()) {
            String actualValue = response.jsonPath().getString(key);
            String expectedValue = expectedData.get(key);
            assertEquals(actualValue, expectedValue,
                    String.format("Assertion Error: The current value is (%s), the expected value is (%s)",
                            actualValue, expectedValue));
        }
    }
}
