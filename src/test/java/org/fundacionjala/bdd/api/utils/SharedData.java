package org.fundacionjala.bdd.api.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class SharedData {

    private int statusCode;
    private RequestSpecification headerResponse;
    private Map<String, Response> responses;

    public SharedData() {
        responses = new HashMap<>();
    }

    public void setResponse(final String key, final Response response) {
        responses.put(key, response);
    }

    public Map<String, Response> getResponse() {
        return responses;
    }

    public void setStatusCode(int statusCode) {

        this.statusCode = statusCode;
    }

    public int getStatusCode() {

        return statusCode;
    }

    public void setHeaderResponse(final RequestSpecification headerResponse) {

        this.headerResponse = headerResponse;
    }

    public RequestSpecification getHeaderResponse() {
        return headerResponse;
    }

}
