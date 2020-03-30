package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.DynamicIdHelper;
import org.fundacionjala.bdd.api.utils.SharedData;

import static io.restassured.RestAssured.given;

public class BoardsPrePostConditions {

    private SharedData sharedData;

    public BoardsPrePostConditions(final SharedData sharedData) {
        this.sharedData = sharedData;
    }

    @After(value = "@deleteBoard")
    public void deleteBoard() {
        String endPoint = DynamicIdHelper.buildEndpoint(sharedData.getResponse(), "/1/boards/{Board_ID.id}/");
        Response bodyResponse = given()
                .spec(sharedData.getHeaderResponse())
                .delete(endPoint);
        sharedData.setStatusCode(bodyResponse.getStatusCode());
    }

    @Before(value = "@getToken")
    public void getToken() {
        RequestSpecification headerResponse = given()
                .baseUri("https://api.trello.com")
                .contentType("Application/Json")
                .queryParam("token", EnvReader.getInstance().getApiToken())
                .queryParam("key", EnvReader.getInstance().getApiKey());
        sharedData.setHeaderResponse(headerResponse);
    }
}
