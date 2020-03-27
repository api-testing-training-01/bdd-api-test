package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.SharedData;

import static io.restassured.RestAssured.given;

public class BoardsPrePostConditions {

    private SharedData sharedData;

    public BoardsPrePostConditions(final SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @After(value = "@deleteBoard")
    public void deleteBoard() {
        given()
                .baseUri("https://api.trello.com")
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .delete("https://api.trello.com/1/boards/".concat(sharedData.getId()));

    }

    @Before(value = "@createBoard")
    public void createBoard() {
        RequestSpecification headerResponse = given()
                .baseUri("https://api.trello.com")
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey());
        Response bodyResponse = given()
                .spec(headerResponse)
                .body("{\"name\":\" GP Board AUTO\",\"desc\":\"testing... boards\"}")
                .post("https://api.trello.com/1/boards");
        JsonPath bodyResponseBoards = new JsonPath(bodyResponse.getBody().asString());
        String boardId = bodyResponseBoards.getString("id");
        sharedData.setBoardId(boardId);
    }
}
