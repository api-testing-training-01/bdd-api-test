package org.fundacionjala.bdd.api.utils.jorge;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    private String boardID;
    private Map<String, Response> responseMap;
    private Map<String, String> paramValue;
    private String key;

    public Helper() {
        boardID = "";
        responseMap = new HashMap<>();
        paramValue = new HashMap<>();
    }

    public void setBoardID(final String id) {
        this.boardID = id;
    }

    public String getBoardID() {
        return boardID;
    }

    public void addResponse(final String key, final Response response) {
        responseMap.put(key, response);
    }

    public Map<String, Response> getResponseMap() {
        return responseMap;
    }

    public void setParamValue(final Map<String, String> paramValue) {
        this.paramValue.putAll(paramValue);
    }

    public Map<String, String> getParamValue() {
        return paramValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }
}
