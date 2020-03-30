package org.fundacionjala.bdd.api.utils;

import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {

    private List<String> ids;
    private Map<String, Response> responses;

    public Helper() {
        ids = new ArrayList<>();
        responses = new HashMap<>();
    }

    public void setResponses(final String id, final Response response) {
        responses.put(id, response);
    }

    public Map<String, Response> getResponses() {
        return responses;
    }

    public void setIds(final String newID) {
        ids.add(newID);
    }

    public List<String> getIds() {
        return ids;
    }



}
