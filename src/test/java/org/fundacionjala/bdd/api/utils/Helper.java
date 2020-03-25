package org.fundacionjala.bdd.api.utils;

public class Helper {

    private String id;

    public Helper() {

    }

    public void addSingleId(final String newId) {
        id = newId;
    }

    public String getSingleId() {
        return id;
    }
}
