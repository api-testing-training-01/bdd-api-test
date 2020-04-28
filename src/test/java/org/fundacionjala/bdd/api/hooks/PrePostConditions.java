package org.fundacionjala.bdd.api.hooks;

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

    @Before(value = "@createBoard")
    public void createBoard() {
        Response responseCreateBoard = given()
                .header("Content-Type", "application/json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("name", "Board created in Pre-Condition")
                .when()
                .post("https://api.trello.com/1/boards");
        String idBoard = responseCreateBoard.jsonPath().getString("id");
        helper.addNewId(idBoard);
        helper.addResponse("responseBoard", responseCreateBoard);
    }
}
