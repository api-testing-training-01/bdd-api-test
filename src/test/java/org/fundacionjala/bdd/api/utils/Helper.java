package org.fundacionjala.bdd.api.utils;

import io.restassured.response.Response;

public class Helper {

    private String id;
    private Response response;

    public Helper() {
    }

    public void setResponse(final Response res) {
        response = res;
        setId(response.jsonPath().getString("id"));
    }

    public Response getResponse() {
        return response;
    }
    public void setId(final String newId) {
        id = newId;
    }

    public String getId() {
        return id;

    }



}
