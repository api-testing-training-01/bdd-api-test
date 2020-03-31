package org.fundacionjala.bdd.api.hooks.jorge;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.jorge.Helper;

import static io.restassured.RestAssured.given;

public class TrelloPrePostConditions {

    private Helper helper;

    public TrelloPrePostConditions(final Helper helper) {
            this.helper = helper;
    }

    @Before(value = "@createBoardPreCondition")
    public void createBoard() {
        Response response = giveTrelloHeader()
                                .queryParam("name", "NewBoardTest")
                                .when()
                                .post("https://api.trello.com/1/boards");
        helper.setBoardID(getId(response.getBody().jsonPath().getString("shortUrl")));
    }

    @After(value = "@deleteBoardPostCondition")
    public void deleteBoard() {
        giveTrelloHeader().when().delete("https://api.trello.com/1/boards/".concat(helper.getBoardID()));
    }

    private static RequestSpecification giveTrelloHeader() {
        return given()
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken());
    }

    private static String getId(final String url) {
        String[] urlArray = url.split("/");
        return urlArray[urlArray.length - 1];
    }
}
