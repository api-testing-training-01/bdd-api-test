package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import static io.restassured.RestAssured.given;

public class JackyPrePostConditions {
    private Helper helper;
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

    @After(value = "@deleteBoardPostCond")
    public void deleteBoard() {

        for (String id:helper.getIds()) {
             given()
                    .spec(setParameters())
                    .when()
                    .delete(url.concat(id));
        }
    }
}
