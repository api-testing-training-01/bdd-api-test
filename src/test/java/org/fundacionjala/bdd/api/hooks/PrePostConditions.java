package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import static io.restassured.RestAssured.given;

public class PrePostConditions {

    private Helper helper;

    public PrePostConditions(final Helper helper) {
        this.helper = helper;
    }

    @After(value = "@deleteBoard")
    public void deleteBoard() {
        String path = "https://api.trello.com/1/boards/".concat(helper.getSingleId());
        given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .when()
                .delete(path);
    }

    @Before(value = "@createBoard")
    public void createBoard() {
        Response createResponse = given()
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", "testAPI005FVTrello")
                .when()
                .post("https://api.trello.com/1/boards");
        String boardId = createResponse.jsonPath().getString("id");
        helper.addSingleId(boardId);
    }
}
