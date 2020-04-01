package org.fundacionjala.bdd.api.utils;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    private String id;
    private Map<String, Response> responses;

    public Helper() {
        responses = new HashMap<>();
    }

    public void addSingleId(final String newId) {
        id = newId;
    }

    public String getSingleId() {
        return id;
    }

    public void addResponse(final String key, final Response response) {
        responses.put(key, response);
    }

    public Map<String, Response> getResponses() {
        return responses;
    }

}
