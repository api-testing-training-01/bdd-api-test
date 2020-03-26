package org.fundacionjala.bdd.api.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    private String boardId;
    private int statusCode;
    private Response bodyResponse;
    private RequestSpecification headerResponse;

    public Helper() {
    }

    public void setBoardId(final String boardId) {
        this.boardId = boardId;
    }

    public String getId() {
        return boardId;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setBodyResponse(final Response bodyResponse){
        this.bodyResponse = bodyResponse;
    }

    public Response getBodyResponse() {
        return bodyResponse;
    }

    public void setHeaderResponse(final RequestSpecification headerResponse){
        this.headerResponse = headerResponse;
    }

    public RequestSpecification getHeaderResponse() {
        return headerResponse;
    }
}
