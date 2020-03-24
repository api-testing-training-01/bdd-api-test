package org.fundacionjala.bdd.api.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.fundacionjala.bdd.api.EnvReader;
import org.fundacionjala.bdd.api.utils.Helper;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PrePostConditions {

    private Helper helper;

    public PrePostConditions(final Helper helper) {
        this.helper = helper;
    }

    @Before(value = "@createProjectPreCond1", order = 0)
    public void createProject() {
        System.out.println("Executing before scenario");
    }

    @Before(value = "@createProjectPreCond2", order = 1)
    public void createProject2() {
        System.out.println("Executing before scenario");
    }

    @After(value = "@deleteCreatedProject")
    public void deleteCreatedProject() {
        List<String> ids = helper.getIds();
        for (String id : ids) {
            given().header("X-TrackerToken", EnvReader.getInstance().getApiToken())
                    .header("Content-Type", "application/json")
                    .when()
                    .delete("https://www.pivotaltracker.com/services/v5/projects/".concat(id));
        }
    }
}
