package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import static io.restassured.RestAssured.given;

public class JackyPrePostConditions {
    private Helper helper;
    private Response response;
    private final String url;

    public JackyPrePostConditions(final Helper helper) {
        this.helper = helper;
        url = "https://api.trello.com/1/boards/";
    }

    public RequestSpecification setParameters() {
         return given()
                .baseUri(url)
                .contentType("application/json")
                .queryParam("key", EnvReader.getInstance().getApiKey())
                .queryParam("token", EnvReader.getInstance().getApiToken());

    }

    @Before(value = "@createBoardPreCond")
    public void createBoard() {
         response = given()
                .spec(setParameters())
                .body("{\"name\":\"New Board Created\",\"desc\":\"new board created from intellij\"}")
                .when()
                .post();
         helper.setResponse(response);
    }

    @After(value = "@deleteBoardPostCond")
    public void deleteBoard() {
       response = given()
               .spec(setParameters())
               .when()
               .delete(url + helper.getId());
    }
}
