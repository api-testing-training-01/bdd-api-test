package org.fundacionjala.bdd.api.utils.alexGarcia;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    private List<String> ids;
    private RequestSpecification initialValues;

    public Helper() {
        ids = new ArrayList<>();
        initialValues = RestAssured.given()
                .baseUri(GlobalVariables.PROJECTS_ENDPOINT_URL)
                .contentType("Application/Json")
                .queryParam("key", GlobalVariables.API_KEY)
                .queryParam("token", GlobalVariables.TOKEN);
    }

    public void addId(final String id) {
        ids.add(id);
    }

    public List<String> getIds() {
        return ids;
    }

    public void removeId(final String id) {
        ids.remove(id);
    }

    public RequestSpecification getInitialValues() {
        return initialValues;
    }
}
