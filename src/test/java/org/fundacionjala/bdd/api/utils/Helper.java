package org.fundacionjala.bdd.api.utils;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    private List<String> ids;

    public Helper() {
        ids = new ArrayList<>();
    }

    public void addNewId(final String id) {
        ids.add(id);
    }

    public List<String> getIds() {
        return ids;
    }
}
