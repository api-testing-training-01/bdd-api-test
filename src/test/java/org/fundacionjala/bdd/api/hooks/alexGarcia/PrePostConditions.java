package org.fundacionjala.bdd.api.hooks.alexGarcia;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.fundacionjala.bdd.api.utils.alexGarcia.GlobalVariables;
import org.fundacionjala.bdd.api.utils.alexGarcia.Helper;

import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ParamConfig.UpdateStrategy.REPLACE;
import static io.restassured.config.ParamConfig.paramConfig;
import static org.testng.Assert.assertEquals;

public class PrePostConditions {
    private static Response response;

    private Helper helper;
    public PrePostConditions(final Helper helper) {
        this.helper = helper;
    }

    @Before(value = "@createDefaultBoard")
    public void createDefaultBoard() {
        String  boardId = null;
        response = helper.getInitialValues()
                .config(config().paramConfig(paramConfig().queryParamsUpdateStrategy(REPLACE)))
                .queryParam("name", "ApiTesting1")
                .queryParam("desc", "Aprendiendo a usar Cucumber")
                .post("/boards");
        boardId = response.jsonPath().getJsonObject("id");
        helper.addId(boardId);
    }

    @After()
    public void teardown() {
        List<String> boardList = helper.getIds();
        for (String boardId : boardList) {
            Response deleteResponse = helper.getInitialValues()
                    .delete("/boards/" + boardId);
            assertEquals(GlobalVariables.OK_STATUS_CODE, deleteResponse.statusCode());
        }
    }
}
