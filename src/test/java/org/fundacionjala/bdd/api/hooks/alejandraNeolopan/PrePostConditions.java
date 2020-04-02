package org.fundacionjala.bdd.api.hooks.alejandraNeolopan;

import io.cucumber.java.After;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.alejandraNeolopan.PicoContainer;

import static io.restassured.RestAssured.given;

public class PrePostConditions {
    private PicoContainer helper;

    public PrePostConditions(final PicoContainer helper) {
        this.helper = helper;
    }

    @After(value = "@cleanBoardPostCond")
    public void deleteBoard() {
        String url = "https://api.trello.com";
        String token = EnvReader.getInstance().getApiToken();
        String key = EnvReader.getInstance().getApiKey();
        for (String id: helper.getIds()) {
            given()
                    .header("Content-Type", "application/json")
                    .when()
                    .delete(url + "/1/boards/" + id + "?key={key}&token={token}", key, token);
        }
    }
}
