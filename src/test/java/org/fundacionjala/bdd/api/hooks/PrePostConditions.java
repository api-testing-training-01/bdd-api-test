package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import java.util.List;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;


public class PrePostConditions {

    private static final String ENDPOINT_URL = "https://api.trello.com/1";
    private Helper helper;

    public PrePostConditions(final Helper helper) {
        this.helper = helper;
    }

    public RequestSpecification requestSpecification() {
        return given()
                .baseUri(ENDPOINT_URL)
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey());
    }

    @After(value = "@deleteBoardTrello")
    public void deleteBoard() {
        List<String> ids = helper.getIds();
        for (String id : ids) {
            given()
                    .spec(requestSpecification())
                    .when()
                    .delete("/boards/" + id);
        }
    }

    @Before(value = "@createBoardTrello")
    public void createBoard() {
        Response createResponse = given()
                .spec(requestSpecification())
                .queryParam("name", "BoardTrelloCucumber")
                .when()
                .post("/boards");
        String boardId = createResponse.jsonPath().getString("id");
        helper.addNewId(boardId);
    }
}
